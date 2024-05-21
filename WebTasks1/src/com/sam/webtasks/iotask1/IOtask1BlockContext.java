package com.sam.webtasks.iotask1;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.basictools.ProgressBar;

public class IOtask1BlockContext {
	private static IOtask1Block blockContext;

	public static void setContext(IOtask1Block block) {
		blockContext = block;
	}

	public static IOtask1Block getContext() {
		return (blockContext);
	}
	
	public static void setNtargets(int nTargets) {
		blockContext.nTargets = nTargets;
	}

	// use these methods to set and find out the currently clicked circle, and check
	// offloading status
	public static void setClickedCircle(int clickedCircle) {
		blockContext.clickedCircle = clickedCircle;

		for (int i = 0; i < blockContext.notYetOffloaded.size(); i++) {
			if (blockContext.notYetOffloaded.get(i) == clickedCircle) {
				blockContext.notYetOffloaded.remove(i);
			}
		}

		// add clicked circle to list of offloaded circles, if it has been clicked out
		// of sequence and is not already in the list
		if (clickedCircle != blockContext.nextCircle) {
			if (!blockContext.allOffloaded.contains(clickedCircle)) {
				blockContext.allOffloaded.add(clickedCircle);
			}
		}
	}
	
	public static void setOffloadCondition(int offloadCondition) {
		blockContext.offloadCondition = offloadCondition;
	}
	

	public static boolean allOffloaded() {
		// automatically return true if reminders are not mandatory
		if ((blockContext.offloadCondition == Names.REMINDERS_NOTALLOWED) | (blockContext.offloadCondition == Names.REMINDERS_OPTIONAL)) {
			return (true);
		} else if (blockContext.offloadCondition == Names.REMINDERS_MANDATORY_TARGETONLY) {
			if (blockContext.notYetOffloaded.size() == 0) {
				return (true);
			} else {
				return (false);
			}
		} else { // reminders mandatory anycircle
			if (blockContext.allOffloaded.size() >= blockContext.nTargets) {
				return (true);
			} else {
				return (false);
			}
		}
	}
	
	public static void targetHit() {
		blockContext.targetHit=true;
	}
	
	public static boolean targetHitStatus() {
		return(blockContext.targetHit);
	}

	public static int getClickedCircle() {
		return (blockContext.clickedCircle);
	}

	// use these methods to set and find which side of the box the circle has been
	// dragged to
	public static void setExitFlag(int exitFlag) {
		blockContext.exitFlag = exitFlag;
	}

	public static int getExitFlag() {
		return (blockContext.exitFlag);
	}

	// use these methods to set and find out which is the next circle in the
	// sequence
	public static void setNextCircle(int nextCircle) {
		blockContext.nextCircle = nextCircle;
	}

	public static int getNextCircle() {
		return (blockContext.nextCircle);
	}

	public static boolean incrementNextCircle() { // returns true if there are more circles to go
		blockContext.nextCircle++;

		if (blockContext.nextCircle < blockContext.nCircles) {
			return (true);
		} else {
			return (false);
		}
	}

	public static boolean allCirclesRemoved() {
		if ((blockContext.clickedCircle + 1) == blockContext.nCircles) {
			return (true);
		} else {
			return (false);
		}
	}

	// find out whether the circle has been dragged to the default exit
	public static boolean defaultExit() {
		if (blockContext.targetCircles[blockContext.exitFlag] == -1) {
			return (true);
		} else {
			return (false);
		}
	}

	// find out whether a correct target response has been made
	public static boolean corectTargetResponse() {
		if (blockContext.targetCircles[blockContext.exitFlag] == blockContext.clickedCircle) {
			return (true);
		} else {
			return (false);
		}
	}

	// increment the trial number at the end of a trial
	public static void incrementTrialNumber() {
		blockContext.currentTrial++;
		
		if (blockContext.incrementProgress) {
			ProgressBar.Increment();
		}
	}
	
	// set and check the doubleClickFlag
	public static void setDoubleClickFlag(boolean doubleClickFlag) {
		blockContext.doubleClickFlag = doubleClickFlag;
	}

	public static boolean getDoubleClickFlag() {
		return (blockContext.doubleClickFlag);
	}

	// set and check the drag start and end timestamps
	public static void setDragStart() {
		blockContext.dragStart = new Date();

		blockContext.dragStartTimeStamp = (int) (blockContext.dragStart.getTime()
				- blockContext.instructionEnd.getTime());
	}

	public static Date getDragStartTime() {
		return (blockContext.dragStart);
	}

	public static int getDragStartTimeStamp() {
		return (blockContext.dragStartTimeStamp);
	}

	public static void setDragEnd() {
		blockContext.dragEnd = new Date();
		blockContext.dragDuration = (int) (blockContext.dragEnd.getTime() - blockContext.dragStart.getTime());
	}

	public static Date getDragEndTime() {
		return (blockContext.dragEnd);
	}

	public static int getDragDuration() {
		return (blockContext.dragDuration);
	}
	
	//get time since trial start
	public static int getTrialTimeStamp() {
		Date timestamp = new Date();
		
		int timestampMS = (int) (timestamp.getTime() - blockContext.instructionEnd.getTime());
		
		return (timestampMS);
	}

	// set and check the redFlashFlag
	public static void setRedFlashFlag(boolean redFlashFlag) {
		blockContext.redFlashFlag = redFlashFlag;
	}

	public static boolean getRedFlashFlag() {
		return (blockContext.redFlashFlag);
	}

	// check whether we should be asking an arithmetic question now
	public static boolean quizCircle() {
		if (blockContext.clickedCircle == blockContext.quizCircle) {
			// this is the quiz circle. before returning true we will set the quiz circle to
			// -1 so the question does not get asked again
			blockContext.quizCircle = -1;

			return (true);
		} else {
			return (false);
		}
	}
	
	public static boolean probeCircle() {
		if (blockContext.clickedCircle == blockContext.probeCircle) {

			//return probe circle to -1
			blockContext.probeCircle = -1;
			
			return (true);
		} else {
			return (false);
		}
	}

	// get the block number, trial number, and nCircles
	public static int getBlockNum() {
		return (blockContext.blockNum);
	}

	public static int getTrialNum() {
		return (blockContext.currentTrial);
	}

	public static int getNCircles() {
		return (blockContext.nCircles);
	}
	
	public static int getNTargets() {
		return (blockContext.nTargets);
	}
	
	public static int getOffloadCondition() {
		return (blockContext.offloadCondition);
	}

	// get the target circles for each side of the box
	public static int getTargetCircle(int side) {
		return (blockContext.targetCircles[side]);
	}
	
	// is the countdown timer running?
		public static boolean countdownTimer() {
			return (blockContext.countdownTimer);
		}
		
		public static int countdownTime() {
			return (blockContext.countdownTime);
		}
		
		public static void setCountdownTime(int cTime) {
			blockContext.countdownTime = cTime;
		}
		
		public static void countdown() {
			if (blockContext.countdownTime > 0) {
				blockContext.countdownTime--;
			}
		}
}
