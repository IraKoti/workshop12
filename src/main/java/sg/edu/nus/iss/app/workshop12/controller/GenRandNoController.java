package sg.edu.nus.iss.app.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.workshop12.exception.RandNoException;
import sg.edu.nus.iss.app.workshop12.models.Generate;

@Controller
@RequestMapping(path = "/rand")
public class GenRandNoController {
    @GetMapping(path = "/show")
    public String showRandForm(Model model)
    {
        Generate g = new Generate();
        model.addAttribute("generateObj", g);
        return "generate";
    }

    @PostMapping(path="/generate")
    public String postRandNum(@ModelAttribute Generate generate, Model model){
        this.randomizeNum(model, generate.getNumberVal());
        System.out.println("post mapping");
        return "result";
    }


    // sample is http://localhost:8080/rand/generate?numberVal=5
    @GetMapping(path = "/generate")
    public String generateRandNumByGet(@RequestParam Integer numberVal, Model model)
    {
        this.randomizeNum(model, numberVal.intValue());
        System.out.println("get mapping");
        return "result";
    }

    // sample is http://localhost:8080/rand/generate/10
    @GetMapping(path="/generate/{numberVal}")
    public String generateRandNumByGetPV(@PathVariable Integer numberVal, Model model){
        this.randomizeNum(model, numberVal.intValue());
        return "result";
    }

    public void randomizeNum(Model model, Integer noOfGenerate)
    {
        Integer maxNumber = 30, minNumber=1;
        String[] imgNumbers = new String[maxNumber+1];

        if(noOfGenerate<minNumber || noOfGenerate>maxNumber)
        {
            throw new RandNoException();
        }

        for(int i=0;i<=maxNumber;i++)
        {
            imgNumbers[i] = "number"+(i+1)+".jpg";
        }

        List<String> selectedImage = new ArrayList<String>();
        Random rnd = new Random();
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        while(uniqueResult.size()<noOfGenerate)
        {
            Integer rndNumber = rnd.nextInt(maxNumber);
            uniqueResult.add(rndNumber);
        }

        Iterator<Integer> it = uniqueResult.iterator();
        Integer curNum = null;
        while(it.hasNext())
        {
            curNum = it.next();
            System.out.println(curNum);
            selectedImage.add(imgNumbers[curNum.intValue()]);
        }
        model.addAttribute("numberRandomNum", noOfGenerate);
        model.addAttribute("randNumResult", selectedImage.toArray());
    }
}