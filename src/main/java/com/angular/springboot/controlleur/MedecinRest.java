package com.angular.springboot.controlleur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.angular.springboot.model.AdvanceSearch;
import com.angular.springboot.model.Medecin;
import com.angular.springboot.model.Recherche;
import com.angular.springboot.service.MedecinService;

@RestController
public class MedecinRest {
	
	@Autowired
	MedecinService medservice;
	
	@RequestMapping(value="/medecins/add",method= RequestMethod.GET)
	public ModelAndView hello() {
		ModelAndView model= new ModelAndView();
		Medecin med = new Medecin();
		model.addObject("medecinForm", med);
		model.setViewName("Medecin_Form");
		return(model);
		
		
	}
	@RequestMapping(value="/medecins/save",method= RequestMethod.POST)
 public	ModelAndView save(@ModelAttribute("medecinForm") Medecin med) {
		medservice.saveOrUpdate(med);
		return (new ModelAndView("redirect:/medecins/list"));
	
	}
	@RequestMapping(value="/medecins/list",method= RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model= new ModelAndView();
		List<Medecin> listmed = medservice.getAllMedecins();
		model.setViewName("Consulte_Medecins");
		model.addObject("listemedecins", listmed);
		return(model);
		
	}
	
	@RequestMapping(value = "/medecins/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") long id) {
		medservice.deleteMedecin(id);
		return new ModelAndView("redirect:/medecins/list");
	}
	
	@RequestMapping(value = "/medecins/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable("id") long id) {
		ModelAndView model = new ModelAndView();

		Medecin med = medservice.getMedecinById(id);
		model.addObject("medecinForm", med);
		model.setViewName("Medecin_EditForm");

		return model;
	}
	@RequestMapping(value = "/medecins/find", method = RequestMethod.GET) 
	public ModelAndView find() {
		ModelAndView model= new ModelAndView();
		Recherche res= new Recherche();
		model.addObject("findForm", res);
		model.setViewName("Find_Medecin");
		return (model);
	
	}
	
	
	@RequestMapping(value="/medecins/recherche",method= RequestMethod.POST)
	public ModelAndView recherche (@ModelAttribute ("findForm") Recherche res) {
		ModelAndView model= new ModelAndView();
		List<Medecin> listmed = medservice.findBySpecialite(res.getSpecialite().trim());
		res.setListmed(listmed);
		model.addObject("findForm", res);
		model.setViewName("Find_Medecin");
		return (model);
		
		
	}
	
	@RequestMapping(value = "/medecins/find2", method = RequestMethod.GET) 
	public ModelAndView find2() {
		ModelAndView model= new ModelAndView();
		AdvanceSearch res= new AdvanceSearch();
		model.addObject("findForm", res);
		model.setViewName("Advanced_Search");
		return (model);
	}
	
	@RequestMapping(value="/medecins/search",method= RequestMethod.POST)
	public ModelAndView search (@ModelAttribute("findForm") AdvanceSearch res) {
		ModelAndView model= new ModelAndView();
		List<Medecin> listmed = medservice.Search(res.getSpecialite().trim(), res.getPseudo().trim());
		res.setListmed(listmed);
		model.addObject("findForm", res);
		model.setViewName("Advanced_Search");
		return (model);
		
		
	}
	
}