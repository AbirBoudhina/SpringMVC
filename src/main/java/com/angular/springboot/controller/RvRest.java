package com.angular.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.angular.springboot.model.Client;
import com.angular.springboot.model.Medecin;
import com.angular.springboot.model.Rv;
import com.angular.springboot.service.ClientService;
import com.angular.springboot.service.MedecinService;
import com.angular.springboot.service.RvService;


@RestController
public class RvRest {

	@Autowired
	ClientService clientservice;

	@Autowired
	MedecinService medservice;

	@Autowired
	RvService rdvservice;

	@RequestMapping(value = "/rdvs/add", method = RequestMethod.GET)
	public ModelAndView hello() {
		ModelAndView model = new ModelAndView();
		Rv rdv = new Rv();
		
		List<Medecin> listmed = medservice.getAllMedecins();
		model.addObject("meds", listmed);
		
		List<Client> listclient = clientservice.getAllClients();
		model.addObject("clients", listclient);

		model.addObject("rdvForm", rdv);
		model.setViewName("Rv_Form");
		return (model);

	}

	@RequestMapping(value = "/rdvs/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("rdvForm") Rv rdv) {

		Medecin med = medservice.getMedecinById(rdv.getMedecin().getId());
		rdv.setMedecin(med);

		Client c = clientservice.getClientById(rdv.getClient().getId());
		rdv.setClient(c);

		rdvservice.saveorupdate(rdv);
		return (new ModelAndView("redirect:/rdvs/list"));

	}

	@RequestMapping(value = "/rdvs/confirm", method = RequestMethod.GET)
	public String hello_med() {
		return ("tu es la bienvenue dans l'espace de gestion des endez-vous");
	}

	@RequestMapping(value = "/rdvs/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		List<Rv> listrdvs = rdvservice.getAllRvs();
		model.setViewName("Consulte_Rvs");
		model.addObject("listerdvs", listrdvs);
		return (model);

	}

	@RequestMapping(value = "/rdvs/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable long id) {
		ModelAndView model = new ModelAndView();
		
		List<Medecin> listmed = medservice.getAllMedecins();
		model.addObject("meds", listmed);
		
		List<Client> listclient = clientservice.getAllClients();
		model.addObject("clients", listclient);

		Rv rdv = rdvservice.getRvById(id);
		model.addObject("rdvForm", rdv);
		model.setViewName("Rv_EditForm");

		return model;
	}

	@RequestMapping(value = "/rdvs/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") long id) {
		rdvservice.deleteRv(id);
		return new ModelAndView("redirect:/rdvs/list");
	}

}
