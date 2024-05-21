//This class defines all the parameters required to run a block of trials

package com.sam.webtasks.iotask1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.gwt.user.client.Random;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.client.Params;
import com.sam.webtasks.client.SequenceHandler;

public class IOtask1Block {
	//set defaults for the parameters
	
	//number of trials to run
	public int nTrials=1;    
	
	//number of circles on the screen
	public int nCircles=15;  
	
	//allow any circle to be moved?
	public int offloadCondition=Names.REMINDERS_OPTIONAL;
	
	//display instruction about the offloading condition?
	public boolean offloadInstruction = false;

	//what block number is this? Useful to store alongside performance data
	public int blockNum=1;      
	
	//which trial number is this?
	public int currentTrial=0;   

	//which side of the box for nontargets? (usually 4, which is bottom)
	public int defaultExit=4;    
	
	// include a countdowntimer?
	public boolean countdownTimer = false;
	public int countdownTime = Params.countdownTime; //number of seconds to start clock at

	
	//should target feedback be shown, i.e. green for correct target response, red for incorrect
	public boolean showTargetFeedback=true;
	
	//should the progress bar be incremented?
	public boolean incrementProgress=false;
	
	//set up targets
	public int nTargets=1;               //number of targets
	
	//The nTargets variable will be used to present all trials with a fixed number
	//of targets, unless the targetList variable below is set up.
	//This is done by adding integers to the variable, each specifying the number
	//of targets for a single trial. E.g. if there are 4 trials, you would run
	//targetList.add(1);
	//targetList.add(1);
	//targetList.add(3);
	//targetList.add(3);
	//to add two trials with one target and two trials with three targets
	public ArrayList<Integer> targetList = new ArrayList<Integer>();
	
	//should the ordering of trials in the targetList be randomised?
	public boolean targetShuffle = true;
		
	//we also create an analogous system to set the offload conditions
	public ArrayList<Integer> offloadConditionList = new ArrayList<Integer>();
	
	public boolean offloadConditionShuffle = true;
	
	
	//should we ask arithmetic questions?
	public boolean askArithmetic = false;
	
	//should we include thought probes?
	public boolean thoughtProbe = false;
	
	public int[] thoughtProbeTrials;


	//this method actually runs the block
	public void Run() {
		/*
		 *first set up the number of targets for each trial*
		 */
		if (targetList.size() == 0) { //no list of targets has been set up
			for (int i = 0; i < nTrials; i++) {
				//fill up the list with the number of targets defined by the nTargets variable
				targetList.add(nTargets);
			}
		} else { //a specific set of targets for each trial has been set up
			if (targetShuffle) { //should they be presented in random order?
				//shuffle list
				for (int i = 0; i < targetList.size(); i++) {
					Collections.swap(targetList, i, Random.nextInt(targetList.size()));
				}
			}
		}
		
		if (offloadConditionList.size() == 0) { //no list of offload conditions has been set up
			for (int i = 0; i < nTrials; i++) {
				//fill up the list with the offload condition defined by the offloadCondition variable
				offloadConditionList.add(offloadCondition);
			}
		} else { //a specific set of offload conditions for each trial has been set up
			if (offloadConditionShuffle) { //should they be presented in random order?
				//shuffle list
				for (int i = 0; i < offloadConditionList.size(); i++) {
					Collections.swap(offloadConditionList,  i,  Random.nextInt(offloadConditionList.size()));
				}
			}			
		}
		
		/*
		 *now save all the context about this block to the IOtask1BlockContext class*
		 */		
		IOtask1BlockContext.setContext(this);
		
		/*
		 * now start subloop 2 of the sequence handler, which runs a trial of the task
		 */
		SequenceHandler.SetLoop(2, true);
		SequenceHandler.Next();
	}
	
	//the variables below are used during trials of the task. they don't need to be set in advance
	
	//each of the four corners (initialised to -10) can be set up as a default exit (-1) or assigned to one of the targets
	public int[] targetCircles = {0, -10, -10, -10, -10}; 
	
	//which circle should next be clicked in the sequence?
	public int nextCircle = 0;
	
	//which circle has actually been clicked?
	public int clickedCircle = 0;
	
	//if a circle has been dragged out of the box, which side has it been dragged to?
	public int exitFlag = 0;
	
	//check whether the first circle has been double-clicked, if so a double-click
	//on the final circle exits the task, to help speed up debugging
	public boolean doubleClickFlag = false;
	
	//these timestamps store the beginning and end of the instructions screen, so we can calculate the reading time
	public Date instructionStart = new Date();
	public Date instructionEnd = new Date();
	public int instructionReadingTime = 0;
	
	//these timestamps store the beginning and end of each drag so that the circle drag time can be calculated
	public Date dragStart = new Date();
	public Date dragEnd = new Date();
	public int dragDuration = 0;
	
	//we also calculate a timestamp for each dragstart relative to the beginning of the trial
	public int dragStartTimeStamp = 0;
	
	//after which circle should we interrupt the participant with an arithmetic question?
	public int quizCircle = -1; //-1 means that there will be no interruption
	
	//after which circle should we interrupt the participant with a thought probe?
	public int probeCircle = -1;
	
	//this flag is set when a red flash has been triggered (incorrect circle in the sequence has been dragged out)
	//we use this to avoid getting in an endless loop of red flashes
	public boolean redFlashFlag = false;
	
	//make a list of target circles that have not yet been offloaded.
	//we can use this for the mandatory offloading condition (targets only)
	public ArrayList<Integer> notYetOffloaded = new ArrayList<Integer>();
	//here we collect a list of all circles that have been offloaded.
	//we can use this for the mandatory offloading condition (any circles)
	public ArrayList<Integer> allOffloaded = new ArrayList<Integer>();
	
	//have any targets been successfully detected?
	public boolean targetHit = false;
}
