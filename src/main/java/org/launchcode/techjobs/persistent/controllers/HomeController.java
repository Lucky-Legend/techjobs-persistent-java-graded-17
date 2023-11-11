package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	    model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model,
                                    @RequestParam(required = false) int employerId,
                                    @RequestParam(required = false) List<Integer> skills) {

//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Add Job");
//            model.addAttribute("employers", employerRepository.findAll());
//            model.addAttribute("skills", skillRepository.findAll());
//            return "add";
//        } else {
//            Optional optEmployer = employerRepository.findById(employerId);
//            Employer employer = (Employer) optEmployer.get();
//            newJob.setEmployer(employer);
//
//            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
//            newJob.setSkills(skillObjs);
//
//            jobRepository.save(newJob);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
        }

        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        optionalEmployer.ifPresent(newJob::setEmployer);

        if (skills != null) {
            List<Skill> skillsResult = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillsResult);
        } else {
            newJob.setSkills(new ArrayList<>());
        }

        jobRepository.save(newJob);

            return "redirect:";
        }


    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            return "view";
        }
        return "redirect:";
    }
}
