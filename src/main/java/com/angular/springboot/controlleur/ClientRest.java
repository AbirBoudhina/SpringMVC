package com.angular.springboot.controlleur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.angular.springboot.model.Client;
import com.angular.springboot.service.ClientService;


@RestController
public class ClientRest {

	@Autowired
	ClientService clientservice;
	
	@RequestMapping(value="/clients",method= RequestMethod.GET)
	public String Bonjour() {
		return "tu es la bienvenue dans le module de gestion des clients"; 

	}
		
	@RequestMapping(value="/clients/test",method= RequestMethod.GET)
	public String test() {
		Client c = new Client(10102, "abir","boudhina");
		clientservice.saveorupdate(c);
		return "client Ajouté";
		
	}

	@RequestMapping(value="/clients/add",method= RequestMethod.GET)
	public ModelAndView hello() {
		ModelAndView model= new ModelAndView();
		Client c = new Client();
		model.addObject("clientForm", c);
		model.setViewName("Client_Form");
		return(model);
		
		
	}
	@RequestMapping(value="/clients/save",method= RequestMethod.POST)
 public	ModelAndView save(@ModelAttribute("clientForm") Client c) {
		clientservice.saveorupdate(c);
		return (new ModelAndView("redirect:/clients/list"));
	
	}
	@RequestMapping(value="/clients/list",method= RequestMethod.GET)
	public ModelAndView  list() {
		ModelAndView model= new ModelAndView();
		List<Client> listcl = clientservice.getAllClients();
		model.setViewName("Consulte_Clients");
		model.addObject("listeclients", listcl);
		return(model);
		
	}
	
	@RequestMapping(value = "/clients/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable long id) {
		ModelAndView model = new ModelAndView();

		Client cl = clientservice.getClientById(id);
		model.addObject("clientForm", cl);
		model.setViewName("Client_EditForm");

		return model;
	}

	@RequestMapping(value = "/clients/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") long id) {
		clientservice.delete(id);
		return new ModelAndView("redirect:/clients/list");
	}
	

}
