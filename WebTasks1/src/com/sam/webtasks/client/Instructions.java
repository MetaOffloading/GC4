package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;

public class Instructions {

	public static String Get(int index) {
		String i = "";

		switch (index) {
		case 1:
			i="In this experiment you will have a simple task to do.<br><br>"
                  + "You will see several yellow circles inside a box. "
                  + "Inside each circle will be a number. <br><br>"
                  + "Using your mouse, you can click on these circles to "
                  + "move them around. Your task is to drag them to the bottom "
                  + "of the box in sequence. "
                  + "Please start by dragging 1 all the way to the bottom. "
                  + "This will make it disappear. Then drag 2 to the bottom, then 3, "
                  + "and so on.<br><br>"
                  + "Click below to practise the task.";
			break;
		case 2:
			i="Now you will continue the same task, but sometimes there will be something else to "
					+ "do.<br><br>As well as dragging each circle in turn to the "
					+ "bottom of the screen, you will be instructed to drag one or more "
					+ "of the circles to another part of the box. For "
					+ "instance, you might be told that you should drag number 11 "
					+ "to the left of the box instead of "
					+ "the bottom.<br><br>You will still be able to drag any "
					+ "circle to the bottom of the box, but you should try to "
					+ "remember to drag these special circles to the instructed "
					+ "location. ";
			break;
		case 3:
			i="Now you will do the task again, but this time there will be three special "
                  + "circles to remember.";
			break;
		case 4:
			i="One last thing: the task may interrupt at certain points, and a screen will "
					+ "appear asking you to tell us how much your attention has drifted from "
					+ "the circle-dragging memory task.<br><br>You will be asked to respond by dragging a slider "
					+ "somewhere between the options of On Task (meaning that you were 100% focused "
					+ "on the circle-dragging memory task, not thinking about anything else) and Off Task "
					+ "(meaning that you were 100% focused "
					+ "on other things, like your own thoughts, feelings about the experiment, "
					+ "or some other distraction in your "
					+ "environment, rather than focusing on doing the task itself).<br><br>"
					+ "People usually find that they are somewhere between these two extremes most of the time."
					+ "<br><br>"
					+ "<b>Important:</b> When you get this question, you should just answer about "
					+ "your thoughts in the precise moment before it appeared. ";
			break;
		case 5:
			i="Thank you. After you get the question about being On Task or Off Task, you will "
					+ "also be asked to estimate whether any Off Task thoughts were <b>intentional</b> "
					+ "or <b>unintentional</b>. "
					+ "By <i>intentional</i> we mean when you deliberately engage in "
					+ "thinking about something unrelated to the current task. By contrast, "
					+ "<i>unintentional</i> thoughts occur when something pops up into your mind that makes "
					+ "you drift from the task, but you did not decide to do it deliberately.<br><br>"
					+ "It's perfectly normal for both these experiences to happen in a task like this, "
					+ "as it can be quite long and boring, and they don't necessarily indicate anything wrong "
					+ "with how you are doing the task.<br><br>If your thoughts were completely On Task you can "
					+ "make any response, in this case it does not matter where you drag the slider.";
		    break;
		case 6:
			i="When you get these questions, please be as honest as possible. It is perfectly normal "
					+ "for your mind to wander when you do a task like this, so there is nothing wrong "
					+ "with saying that you were distracted, either intentionally or unintentionally.<br><br>"
					+ "Given that the task is quite boring, it would be unlikely that anyone will remain totally "
					+ "focused on it for the entire session. For this reason, we do not expect you to respond "
					+ "that you are always focused on the task.<br><br>"
					+ "It is most important to us that you answer as honestly as possible. Whatever you "
					+ "say to these questions will not affect your approval for taking part in this experiment.";
			break;
		case 7:
			i="One way to make the task easier is dragging the special circles near to the "
					+ "instructed edge of the box to help you remember.<br><br> " 
					+ "For example, if you had to remember to drag 12 to the left of the box, "
					+ "you could drag it near to there at the beginning, before you drag the 1. " 
					+ "Then when you eventually got to 12, its location would remind you "
					+ "what to do. Please now try using this strategy to help with the task. ";
			break;
		case 8:
			i="Now the task will start for real. You should try to complete the rest of the experiment "
					+ "in one session and avoid performing any other tasks with your computer.<br><br>"
					+ "Any time you get a question about whether "
					+ "your attention drifted to the task, please answer as accurately as possible and "
					+ "then the computer will automatically skip forward to the next trial.<br><br>"
					+ "You will see a countdown timer at the top of the screen. Please try to complete "
					+ "each trial before the time runs out.<br><br>";
			
			if (Counterbalance.getFactorLevel("offloadOrder") == ExtraNames.EXTERNAL_FIRST) {
				i = i + "You should continue setting reminders by dragging "
					  + "the instructed circles to the edge at the start of each trial.";
			}				
			break;
		case 9:
			i="From now on, you will not be able to set reminders any more. For the rest of "
					+ "the experiment, please just use your memory "
					+ "to remember the special circles.";
			break;
		case 10:
			i = "From now on you should continue setting reminders by dragging the instructed circles "
					+ "to the edge at the start of each trial.";
			break;
		case 11:
			/*
			i = "You have now completed the experiment.<br><br>"
					+ "Please enter the following completion code on the Mechanical Turk website to claim your "
					+ "reward:<br><br><b>" + SessionInfo.rewardCode + "</b>"; 
					*/
			
			i = "You have now completed the experiment. "
					+ "Please click on the link below to complete the experiment and receive payment via Prolific:<br><br>"
					+ "<b><a href=\"https://app.prolific.com/submissions/complete?cc=C1IVJN00\">"
 					+ "CLICK HERE</a></b>";
			
			break;
		}

		return (i);
	}

	public static String InfoText() {
		String expDur = "40";
		String eligibility = "All registered users of Prolific ";
		String nVolunteers = "50";
		
		return ("We would like to invite you to participate in this research project. "
				+ "You should only participate if you want to; choosing not to take part "
				+ "will not disadvantage you in any way. Before you decide whether you "
				+ "want to take part, please read the following information carefully and "
				+ "discuss it with others if you wish. Ask us if there is anything that "
				+ "is not clear or you would like more information.<br><br>"
				+ "We are recruiting approximately " + nVolunteers + " volunteers to "
				+ "take part in an experiment aiming to improve our understanding of human "
				+ "attention and memory. " + eligibility + "are eligible "
				+ "to take part. "
				+ "The experiment will last approximately " + expDur + " minutes. During this experiment you "
				+ "will see various shapes on the screen and you will use your mouse or touchscreen "
				+ "to drag them from one location to another. Full instructions will be provided. "
				+ "There are no anticipated risks or "
				+ "benefits associated with participation in this study.<br><br>"
				+ "It is up to you to decide whether or not to take part. If you choose "
				+ "not to participate, you won't incur any penalties or lose any "
				+ "benefits to which you might have been entitled. If you do "
				+ "decide to take part, you can print out this information sheet and "
				+ "you will be asked to fill out a consent form on the next page. " + "Even after agreeing to take "
				+ "part, you can still withdraw at any time and without giving a reason. This will not affect "
				+ "any benefits that you are entitled to. "
				+ "<br><br>All the information that we collect about you during the course of the research will be "
				+ "kept strictly confidential. The personal information that we will collect is your Prolific ID code. This "
				+ "allows us to link you to your data so that we can process any applicable bonus payments. You will not "
				+ "be able to be identified in any ensuing reports "
				+ "or publications. The results of this research may be published in scientific journals. You can "
				+ "contact the investigator to receive a copy of any published results.<br><br>"
				+ "The data controller for this project will be University College London (UCL). The "
				+ "UCL Data Protection Office provices oversight of UCL activities involving the processing "
				+ "of personal data, and can be contacted at <a href=\"mailto: data-protection@ucl.ac.uk\">"
				+ "data-protection@ucl.ac.uk</a>. UCL's Data Protection Officer can also be contacted at "
				+ "<a href=\"data-protection@ucl.ac.uk\">data-protection@ucl.ac.uk</a>.<br><br>"
				+ "Your personal data will be processed for the purposes outlined in this notice. The "
				+ "legal basis that would be used to process your <i>personal data</i> will be "
				+ "performance of a task in the pulic interest. Your personal data will be processed "
				+ "so long as it is required for the research project. If we are able to anonymise or pseudonymise "
				+ "the personal data you provide we will undertake this, and will endeavour to minimise "
				+ "the processing of personal data wherever possible. Data from this project may "
				+ "also be uploaded to a public repository such as <a href=\"https://osf.io\">https://osf.io</a>. "
				+ "Any data uploaded to a public repository will be anonymised so that it will not "
				+ "be possible to identify you from this data. No personal data will be uploaded publically. "
				+ "If you have any concerns or complaints about this research you should "
				+ "<a href=\"mailto: sam.gilbert@ucl.ac.uk\">contact</a> the principal researcher. "
				+ "If you feel that your complaint has not been handled to your satisfaction you can "
				+ "contact the Chair of the UCL Research Ethics Committee - "
				+ "<a href=\"mailto: ethics@ucl.ac.uk\">ethics@ucl.ac.uk</a>.");
	}

}
