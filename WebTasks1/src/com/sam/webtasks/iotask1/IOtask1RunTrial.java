package com.sam.webtasks.iotask1;

import java.util.Date;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty.Properties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.DragBounds;
import com.ait.lienzo.client.widget.DragContext;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.client.Params;
import com.sam.webtasks.client.SequenceHandler;

public class IOtask1RunTrial {
	public static void Run() {
		// get block context
		IOtask1Block block = IOtask1BlockContext.getContext();

		// get a timestamp, so we can calculate the instruction reading time
		block.instructionEnd = new Date();

		// now calculate reading time and log it
		// we also subtract 500, which is the delay after the instructions are clicked
		// through
		block.instructionReadingTime = (int) (block.instructionEnd.getTime() - 500 - block.instructionStart.getTime());
		int nTargets = block.targetList.get(block.currentTrial);
		
		int offloadCondition = block.offloadConditionList.get(block.currentTrial);
		IOtask1BlockContext.setOffloadCondition(offloadCondition);
		

		String data = block.blockNum + "," + block.currentTrial + "," + nTargets + "," + block.instructionReadingTime;
		PHP.logData("instructionReadingTime", data, false);

		int m = IOtask1BlockContext.countdownTime() / 60;
		int s = IOtask1BlockContext.countdownTime() % 60;
		String tLabel;

		if (s < 10) {
			tLabel = " " + m + ":0" + s;
		} else {
			tLabel = " " + m + ":" + s;
		}

		final Label timerLabel = new Label(tLabel);

		final Timer trialTimer = new Timer() {
			public void run() {
				int minutes = IOtask1BlockContext.countdownTime() / 60;
				int seconds = IOtask1BlockContext.countdownTime() % 60;

				if (seconds < 10) {
					timerLabel.setText(" " + minutes + ":" + "0" + seconds);
				} else {
					timerLabel.setText(" " + minutes + ":" + seconds);
				}

				IOtask1BlockContext.countdown();

				if (IOtask1BlockContext.countdownTime() < 1) {
					timerLabel.addStyleName("red");
					timerLabel.setText("Please try to go faster.");
					cancel();
				}
			}
		};

		// we need to set nCircles as a final variable, so that we can refer to it
		// inside the draghandler
		final int nCircles = block.nCircles;

		// set size parameters
		int xDim = Window.getClientWidth();
		int yDim = Window.getClientHeight();
		int minDim = 0; // smaller of the two dimensions

		if (xDim <= yDim) {
			minDim = xDim;
		} else {
			minDim = yDim;
		}

		final int boxSize = (int) (minDim * IOtask1DisplayParams.boxSize);
		int margin = (int) (boxSize * IOtask1DisplayParams.margin);
		final int circleRadius = (int) (boxSize * IOtask1DisplayParams.circleRadius);

		// set up labels to put inside circles
		String[] labels = new String[block.nCircles];

		for (int i = 0; i < block.nCircles; i++) {
			labels[i] = "" + (i + 1);
		}

		// set up objects on the screen
		final LienzoPanel panel = new LienzoPanel(boxSize, boxSize);

		final VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setWidth(Window.getClientWidth() + "px");
		verticalPanel.setHeight(Window.getClientHeight() + "px");

		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		final VerticalPanel wrapper1 = new VerticalPanel();
		
		final VerticalPanel timerWrapper = new VerticalPanel();
		timerWrapper.setWidth(boxSize + "px");
		timerWrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		timerWrapper.add(timerLabel);
		timerLabel.setStyleName("livePointsDisplay");

		if (IOtask1BlockContext.countdownTimer()) {
			wrapper1.add(timerWrapper);
			trialTimer.cancel();
			IOtask1BlockContext.setCountdownTime(Params.countdownTime);
			
			trialTimer.scheduleRepeating(1000);
		}
		
		wrapper1.add(panel);

		final VerticalPanel wrapper2 = new VerticalPanel();
		wrapper2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper2.add(wrapper1);

		verticalPanel.add(wrapper2);

		RootPanel.get().add(verticalPanel);

		// set up outline
		Layer bgLayer = new Layer();

		Rectangle outlineRectangle = new Rectangle(boxSize, boxSize);
		outlineRectangle.setStrokeColor(ColorName.BLACK).setStrokeWidth(6);

		bgLayer.add(outlineRectangle);

		// put background layer on screen
		panel.add(bgLayer);

		// set up circles
		final Layer circleLayer = new Layer();

		int[] circleX = new int[block.nCircles];
		int[] circleY = new int[block.nCircles];
		final Circle[] circles = new Circle[block.nCircles];
		final Circle[] circleOverlays = new Circle[block.nCircles];
		final Text[] circleText = new Text[block.nCircles];
		final Group[] circleGroup = new Group[block.nCircles];
		int randomFlag = 0;
		block.nextCircle = 0;

		int c = 0;
		int randomCounter = 0;

		// set positions for circles inside box, making sure they're not on top of each
		// other
		while (c < block.nCircles) {
			randomFlag = 1;

			while (randomFlag == 1) {
				randomCounter++;

				if (randomCounter > 5000) {
					randomCounter = 0;
					c = 0; // start again if failing to position the circles appropriately
				}

				randomFlag = 0;

				circleX[c] = Random.nextInt(boxSize - (2 * circleRadius) - (2 * margin)) + circleRadius + margin;
				circleY[c] = Random.nextInt(boxSize - (2 * circleRadius) - (2 * margin)) + circleRadius + margin;

				// now check distance to all the other circles

				for (int cc = 0; cc < c; cc++) {
					int distanceX = circleX[c] - circleX[cc];
					int distanceY = circleY[c] - circleY[cc];

					double distanceAbs = Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5);

					if (distanceAbs < (3 * circleRadius)) {
						randomFlag = 1;
					}
				}
			}

			c++;
		}

		// set up the circle objects, adding the text labels on top with the numbers
		// we also add an overlay to each circle, with a very low alpha so that it's
		// invisible
		// this makes sure that clicking anywhere in the overlay will pick up the circle
		// if it is draggable
		for (c = 0; c < block.nCircles; c++) {
			circles[c] = new Circle(circleRadius);
			circleOverlays[c] = new Circle(circleRadius);
			circleText[c] = new Text(labels[c], "Verdana, sans-serif", null, IOtask1DisplayParams.circleTextSize);
			circleGroup[c] = new Group();

			circles[c].setFillColor(ColorName.YELLOW);
			circles[c].setStrokeColor(ColorName.BLACK);

			circleText[c].setTextAlign(TextAlign.CENTER);
			circleText[c].setTextBaseLine(TextBaseLine.MIDDLE);
			circleText[c].setFillColor(ColorName.BLACK);

			circleOverlays[c].setFillColor(ColorName.BLACK);
			circleOverlays[c].setAlpha(0.0000001);

			circleGroup[c].add(circles[c]);
			circleGroup[c].add(circleText[c]);
			circleGroup[c].add(circleOverlays[c]);
			circleGroup[c].setX(circleX[c]);
			circleGroup[c].setY(circleY[c]);

			switch (offloadCondition) {
			case Names.REMINDERS_NOTALLOWED:
				circleGroup[c].setDraggable(false);
				break;
			default:
				circleGroup[c].setDraggable(true);
				break;
			}

			circleGroup[c].setDragBounds(new DragBounds(1, 1, boxSize, boxSize));

			circleLayer.add(circleGroup[c]);

			// set up the NodeDragStart handler. This is called every time the user begins
			// to drag a circle.
			// We need to figure out which circle has been clicked and set this to the block
			// context
			// TODO: save a time stamp so that we can calculate the drag duration

			final int finalc = c; // need to set up a final version of the c variable so that it works in the code
									// below

			circleGroup[c].addNodeDragStartHandler(new NodeDragStartHandler() {
				public void onNodeDragStart(NodeDragStartEvent event) {
					// reset the double click flag. this is used to quit the task
					// using a double click to the first circle followed by
					// a double click to the last circle
					IOtask1BlockContext.setDoubleClickFlag(false);

					// reset the exit flag, which is only tripped if participant drags circle out of
					// box
					IOtask1BlockContext.setExitFlag(0);

					// set the timestamp for the start of the drag
					IOtask1BlockContext.setDragStart();

					DragContext dC = event.getDragContext();

					// figure out which circle was clicked for (int c = 0; c < nCircles; c++) {
					double xDist = dC.getDragStartX() - circleGroup[finalc].getX();
					double yDist = dC.getDragStartY() - circleGroup[finalc].getY();

					if (Math.pow(xDist, 2) <= Math.pow(circleRadius, 2)) {
						if (Math.pow(yDist, 2) <= Math.pow(circleRadius, 2)) {
							IOtask1BlockContext.setClickedCircle(finalc);
						}
					}
				}
			});

			// set up the NodeDragMove handler. This is used to figure out if a circle has
			// been dragged out of the box
			circleGroup[c].addNodeDragMoveHandler(new NodeDragMoveHandler() {
				public void onNodeDragMove(NodeDragMoveEvent event) {
					IOtask1Block block = IOtask1BlockContext.getContext();

					if (event.getX() <= 0) { // left
						IOtask1BlockContext.setExitFlag(1);
					}

					if (event.getX() >= boxSize) { // right
						IOtask1BlockContext.setExitFlag(2);
					}

					if (event.getY() <= 0) { // up
						IOtask1BlockContext.setExitFlag(3);
					}

					if (event.getY() >= boxSize) { // down
						IOtask1BlockContext.setExitFlag(4);
					}

					if (IOtask1BlockContext.getExitFlag() > 0) { // if the circle has been dragged out of the box
						DragContext dC = event.getDragContext();
						int clickedCircle = IOtask1BlockContext.getClickedCircle();

						if (IOtask1BlockContext.allOffloaded()) { // check whether participant has offloaded before continuing. If reminders are not mandatory, this function automatically returns true
							// is the clicked circle the next one in the sequence?
							if (IOtask1BlockContext.getClickedCircle() == IOtask1BlockContext.getNextCircle()) {
								// fix position of circle on screen
								circles[clickedCircle].setDragBounds(
										new DragBounds(dC.getEventX(), dC.getEventY(), dC.getEventX(), dC.getEventY()));

								if (IOtask1BlockContext.defaultExit()) { // has the circle been dragged to the default
																			// exit?
									// change circle colour to purple
									circles[clickedCircle].setFillColor(ColorName.PURPLE);

									// remove text from circle
									circleText[clickedCircle].setVisible(false);

								} else if (IOtask1BlockContext.corectTargetResponse()) { // correct target response
									if (block.showTargetFeedback) { // change colour of circle
										circles[clickedCircle].setStrokeColor(ColorName.PINK);
										circles[clickedCircle].setStrokeWidth(3);
										circles[clickedCircle].setFillColor(ColorName.GREENYELLOW);
										
										IOtask1BlockContext.targetHit();
									}

									// remove text
									circleText[clickedCircle].setVisible(false);
								} else { // incorrect target response
									if (block.showTargetFeedback) { // change colour of circle
										circles[clickedCircle].setStrokeColor(ColorName.PINK);
										circles[clickedCircle].setStrokeWidth(3);
										circles[clickedCircle].setFillColor(ColorName.RED);
									}

									// remove text
									circleText[clickedCircle].setVisible(false);
								}
							} else {
								if (!IOtask1BlockContext.getRedFlashFlag()) {
									circles[IOtask1BlockContext.getNextCircle()].setFillColor(ColorName.RED);
									circleLayer.draw();

									new Timer() {
										public void run() {
											circles[IOtask1BlockContext.getNextCircle()].setFillColor(ColorName.YELLOW);
											circleLayer.draw();
										}
									}.schedule(400);

									IOtask1BlockContext.setRedFlashFlag(true); // by setting this to true, we prevent
																				// the
																				// code above from being tripped
																				// repeatedly
								}
								IOtask1BlockContext.setExitFlag(0);
							}
						} else {
							// first time someone tries to remove a circle without offloading, give a pop-up
							// window alert
							if (IOtask1BlockContext.defaultExit()) {
								Window.alert("You need to set reminders before you can continue. You can "
									+ "do this by moving the position of special circles.");
							}

						}
					}
				}
			});

			// set up the NodeDragEnd handler. This needs to stop ther timer recording the
			// drag duration,
			// start the animation, and log the data
			circleGroup[c].addNodeDragEndHandler(new NodeDragEndHandler() {
				public void onNodeDragEnd(NodeDragEndEvent event) {
					// set timestamp for the end of the drag and calculate drag duration
					IOtask1BlockContext.setDragEnd();

					// clear red flash flag
					IOtask1BlockContext.setRedFlashFlag(false);

					// log data
					String data = IOtask1BlockContext.getBlockNum() + ",";
					data = data + IOtask1BlockContext.getTrialNum() + ",";
					data = data + IOtask1BlockContext.getNCircles() + ",";
					data = data + IOtask1BlockContext.getNTargets() + ",";
					data = data + IOtask1BlockContext.getOffloadCondition() + ",";
					data = data + IOtask1BlockContext.getNextCircle() + ",";
					data = data + IOtask1BlockContext.getClickedCircle() + ",";
					data = data + IOtask1BlockContext.getDragStartTimeStamp() + ",";
					data = data + IOtask1BlockContext.getDragDuration() + ",";
					data = data + IOtask1BlockContext.getExitFlag() + ",";
					data = data + IOtask1BlockContext.getTargetCircle(1) + ",";
					data = data + IOtask1BlockContext.getTargetCircle(2) + ",";
					data = data + IOtask1BlockContext.getTargetCircle(3) + ",";
					data = data + IOtask1BlockContext.getTargetCircle(4) + ",";
					data = data + TimeStamp.Now();

					PHP.logData("dragEnd", data, false);

					if (IOtask1BlockContext.probeCircle()) { //quit the trial and run a thought probe
						new Timer() {
							public void run() {
								//get time stamp, calculate time since beginning of trial
								Date probeTime = new Date();

								int probeTimeInt = (int) (probeTime.getTime() - block.instructionEnd.getTime());

								String ProbeTimeData = block.blockNum + "," + block.currentTrial + ","  + probeTimeInt;
								PHP.logData("ProbeTimeStamp", ProbeTimeData, false);
								
								RootPanel.get().remove(verticalPanel);
								IOtask1BlockContext.incrementTrialNumber();
								trialTimer.cancel();
								IOtask1BlockContext.setCountdownTime(Params.countdownTime);
								
								//run thought probe
								SequenceHandler.SetLoop(4, true);
								SequenceHandler.Next();
							}
						}.schedule(500);	
					}
					
					// check if moving this circle should trigger an arithmetic question
					if (IOtask1BlockContext.quizCircle()) {
						new Timer() {
							public void run() {
								int number1 = 0, number2 = 0, operation = 0, result = 0;
								boolean OK = false;
								String operationString = "";
								String sum = "";
								String response = "";

								while (!OK) {
									number1 = Random.nextInt(9) + 1;
									number2 = Random.nextInt(9) + 1;
									operation = Random.nextInt(2);

									if (operation == 0) {
										operationString = " + ";
										result = number1 + number2;
									} else {
										operationString = " - ";
										result = number1 - number2;
									}

									if ((result > 0) & (result < 10)) {
										OK = true;
									}
								}

								sum = number1 + operationString + number2;

								int r = -1;

								// set timestamp for question being asked
								Date questionAsked = new Date();

								while ((r < 0) | (r > 10)) {// accept any response between 1 and 9
									response = Window.prompt("What is " + sum + "?", "");

									try {
										r = Integer.parseInt(response);
									} catch (NumberFormatException e) {
										r = -1;
									}
								}

								Date questionAnswered = new Date();
								int arithmeticRT = (int) (questionAnswered.getTime() - questionAsked.getTime());

								String data = number1 + "," + operation + "," + number2 + ",";
								data = data + r + "," + result + "," + arithmeticRT + "," + (r == result);

								PHP.logData("arithmeticQ", data, false);
							}
						}.schedule(400);
					}

					if (IOtask1BlockContext.getExitFlag() > 0) { // circle dragged out of box
						if (IOtask1BlockContext.getClickedCircle() == IOtask1BlockContext.getNextCircle()) { // circle was next in sequence
							if (IOtask1BlockContext.allOffloaded()) {
								// run the animation
								AnimationProperties props = new AnimationProperties();
								props.push(Properties.SCALE(0));

								circles[IOtask1BlockContext.getClickedCircle()].animate(AnimationTweener.LINEAR, props,
										200);

								IOtask1BlockContext.setExitFlag(0); // reset exit flag

								if (IOtask1BlockContext.incrementNextCircle()) { // update which circle is next and make
																					// next one draggable if there are
																					// more
																					// to go
									circleGroup[IOtask1BlockContext.getNextCircle()].setDraggable(true);
								}

								if (IOtask1BlockContext.allCirclesRemoved()) { // end of trial
									new Timer() {
										public void run() {
											RootPanel.get().remove(verticalPanel);
											IOtask1BlockContext.incrementTrialNumber();

											new Timer() {
												public void run() {
													trialTimer.cancel();
													IOtask1BlockContext.setCountdownTime(Params.countdownTime);
													
													SequenceHandler.Next();
												}
											}.schedule(500);
										}
									}.schedule(500);
								}
							}
						}

					}

				}
			});
		}

		// add double click handlers, which can be used to quit a trial early (helpful
		// during development/testing of the code)
		circleGroup[0].addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {

			public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event) {
				IOtask1BlockContext.setDoubleClickFlag(true);
			}
		});

		circleGroup[nCircles - 1].addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
			public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event) {
				if (IOtask1BlockContext.getDoubleClickFlag()) {
					RootPanel.get().remove(verticalPanel);
					IOtask1BlockContext.incrementTrialNumber();
					trialTimer.cancel();
					SequenceHandler.Next();
				}
			}
		});

		// make sure that the first circle is draggable, even if offloading is not
		// allowd
		circleGroup[0].setDraggable(true);

		// draw the display
		panel.add(circleLayer);
		circleLayer.draw();
	}
}
