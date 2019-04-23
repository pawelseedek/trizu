package com.trizu.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.trizu.Immutable;
import com.trizu.domain.House;
import com.trizu.domain.Pin;
import com.trizu.message.InMessage;
import com.trizu.message.OutMessage;
import com.trizu.service.HouseService;
import com.trizu.service.PinService;
import com.trizu.service.UserService;
import com.trizu.socket.Connection;
import com.trizu.socket.Server;
import com.trizu.socket.TcpConnection;
import com.trizu.validator.HouseValidator;


@Controller
public class HouseControler implements ApplicationListener<ApplicationReadyEvent>, Connection.Listener {
	
	private Immutable immutable = new Immutable();
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private HouseValidator houseValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Server server;
	
	@Autowired
	private PinService pinService;
	
	@Autowired
	private SimpMessagingTemplate template;
		
	/**
	 * Run TcpServer thread when application is ready
	 */
	@Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        server.start();
		server.addListener(this);
    }

	
	/**
	 * Method listening for message recived from TcpConnection
	 * @param connection - connection from witch the message arrived
	 * @param message - message Object
	 */
	@Override
	public void messageReceived (Connection connection, Object message){
		String msg = new String((byte[]) message);
		if(msg != null || !msg.equals("")) {
				String pinNumber = msg;
				List <Pin> pins = houseService.getHouseByHouseid(connection.getId()).getHousePins();
				for(Pin pin : pins){
					if(pinNumber.equals(String.valueOf(pin.getPinNumber()))) {
						pin.changeState();
						pinService.save(pin);
					}
				}	
		}
		this.template.convertAndSend("/secured/user/specific-house/"+connection.getId(), new OutMessage(getJson(connection.getId())));
	}
	
	/**
	 * Active after new connection to the TcpServer.
	 * Waiting 200ms to let the connected house device send connection id and password.
	 * Then if connection id == null, try 6 times every 500ms. 
	 */
	@Override
    public void connected(Connection connection) {
		int i = 6;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(connection.getId() == null || i == 0){ 
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i--;
		} 
		connection.send((immutable.PINCONFIG+getJson(connection.getId())).getBytes());
	}
	
	
	@Override
    public void disconnected(Connection connection) {
	}
	

	@PostMapping("/addHouse")
	public String addHouse( @ModelAttribute("houseForm") House houseForm,
							BindingResult bindingResult, Principal principal,
							RedirectAttributes redirectAttrs){
        houseValidator.validate(houseForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addHouse";
        }
        
        if(server.getConnectionByHouseId(houseForm.getHouseid()) != null) {
        	Connection connection = server.getConnectionByHouseId(houseForm.getHouseid());
			if(connection.getPassword().equals(houseForm.getPassword())){
				House house;
				 if(houseService.existByIp(houseForm.getHouseid()) == false){
					houseService.save(houseForm);
				}
					house = houseService.getHouseByHouseid(houseForm.getHouseid());
					house.addUser(userService.findByUsername(principal.getName()));
					houseService.save(house);
			}else{
				redirectAttrs.addFlashAttribute("noConnection", "Check Password!");
				return "redirect:/addHouse";
			}
        }
        else {
        	redirectAttrs.addFlashAttribute("noConnection", "Check house ID or device connection!");
        	return "redirect:/addHouse";
        }
        
		return "redirect:/editHouse/"+ houseForm.getHouseid();
	}

    @MessageMapping("/house/{houseid}/{pinId}")
    @SendTo("/secured/user/specific-house/{houseid}")
    @Transactional
    public OutMessage changePinState(InMessage message, @DestinationVariable String houseid, 
										@DestinationVariable String pinId) throws Exception {
		if(!pinId.equals("null")) {
			Pin pin = houseService.getHouseByHouseid(houseid).getPinById(pinId);
				if(pin != null && pin.getPinType()==true) {
					Connection connection = server.getConnectionByHouseId(houseid);
					if(connection != null) {
						connection.send(String.valueOf(pin.getPinNumber()).getBytes());
					}else{
						return new OutMessage("connectionError");
					}
				}
		}
		return new OutMessage(getJson(houseid));
	}


    @PreAuthorize("@houseServiceImpl.hasAccess(#houseid, #principal)")
	@PostMapping("/editHouse/{houseid}")
	public String addPin(@ModelAttribute("pin") Pin pin, @PathVariable("houseid") String houseid, 
						Principal principal, RedirectAttributes redirectAttrs ){
		Connection connection = server.getConnectionByHouseId(houseid);
		if(connection != null) {
			pin.setHouse(houseService.getHouseByHouseid(houseid));
			pinService.save(pin);
			String json = immutable.PINCONFIG+"{\"pins\" : [{\"pinNumber\" : \""+ pin.getPinNumber() +"\", \"pinType\" : \""+pin.getPinType() +"\", \"pinState\" : \""+pin.getState() +"\"}] }";
			connection.send(json.getBytes());
		}else{
			redirectAttrs.addFlashAttribute("connectionError", "Connection Error");
		}
		this.template.convertAndSend("/secured/user/specific-house/"+connection.getId(), new OutMessage(getJson(connection.getId())));
		return "redirect:/editHouse/"+houseid;
	} 
        
	@PreAuthorize("@houseServiceImpl.hasAccess(#houseid, #principal)")
    @GetMapping("/deletePin/{houseid}/{pinid}")
    public String deletePin(Model model, @PathVariable("houseid") String houseid, @PathVariable("pinid") String pinId, Principal principal) {
		Pin pin = houseService.getHouseByHouseid(houseid).getPinById(pinId);
		if(pin != null) {
    		pinService.delete(pin);
    	}
    	return "redirect:/editHouse/"+houseid;
    }
		
	private String getJson(String houseid){
		List<Pin> pins = houseService.getHouseByHouseid(houseid).getHousePins();
		JSONArray ja = new JSONArray();
    	JSONObject obj= new JSONObject();
    	for(Pin p : pins) {
			Map m = new LinkedHashMap(); 
	        m.put("pinId", p.getPinId()); 
	        m.put("pinName", p.getName()); 
	        m.put("pinNumber", p.getPinNumber()); 
	        m.put("pinState", p.getState()); 
	        m.put("pinType", p.getPinType()); 
	        ja.put(m);
		}
    	obj.put("pins", ja);
		return obj.toString();
	}
	

    @PreAuthorize("@houseServiceImpl.hasAccess(#houseid, #principal)")
    @GetMapping("/editHouse/{houseid}")
    public String editHouse(Model model, @PathVariable("houseid") String houseid, Principal principal) {
		model.addAttribute("pin", new Pin());
		model.addAttribute("freePins", houseService.getHouseByHouseid(houseid).getFreePins());
		model.addAttribute("housePins", houseService.getHouseByHouseid(houseid).getHousePins());
    	return "editHouse";
    }
	

    
	@GetMapping("/selectHouse")
	public String choseHouse(Model model, Principal principal){
		model.addAttribute("houseList" , houseService.getHousesByUsername(principal.getName()));
		return "selectHouse";
	}
	
	@GetMapping("/addHouse")
	public String addHouse(Model model){
		model.addAttribute("houseForm", new House());
		return "addHouse";
	}
	
    @PreAuthorize("@houseServiceImpl.hasAccess(#houseid, #principal)")
	@GetMapping("/housePanel/{houseid}")
	public String getHousePanel(Model model, @PathVariable("houseid") String houseid, Principal principal ){
		model.addAttribute("houseid", houseid);
		return "housePanel";
	}
	
}	