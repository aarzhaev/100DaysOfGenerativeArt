import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class day51 extends PApplet {

/*== DAY 51 == [SUN FEB 28 2016] == */
/*
 * "Harmonograph - I"
 * Harmonograph based on the original pendulum based design. Going to be exploring this theme in depth the next couple of posts.
 */

int fCount = 14*30;
int fDiv = 3;

float length0 = 120;
float length1 = 100;
float angle0 = PI/2;
float angle1 = PI/2;
float delayPerc = 0.5f;
float graphScale = 1.6f;

Pendulum pend0, pend1;
float x0, x1, y0, y1;

public void setup() {
  
  
  background(99, 36, 36);
  frameRate(30);

  pend0 = new Pendulum(angle0, length0);
  pend1 = new Pendulum(angle1, length1);
  updatePendulums();
}

public void updatePendulums() {
  pend0.update();
  pend1.update();

  x0 = x1; y0 = y1;
  x1 = graphScale * pend0.getHorizPos();
  y1 = graphScale * pend1.getHorizPos();
}

public void drawFrame() {
  updatePendulums();

  // clear top area so we can snapshot render pendulums
  fill(99, 36, 36);
  noStroke();
  rect(0,0, width, length0*1.2f);

  // draw from anchor point attached to "roof" of sketch
  stroke(0);
  strokeWeight(2);
  fill(255, 250, 218);

  pushMatrix();
  translate(width/4, 0);
  pend0.draw();
  translate(width/2, 0);
  pend1.draw();
  popMatrix();

  // draw composite shape below pendulums
  translate(width/2, (height-120)/2 + 120);
  // determine stroke weight based on distance - emulate pen drawing
  float dist = dist(x0, y0, x1, y1);
  strokeWeight(map(dist, 0, 20, 1.5f, 0.3f));
  stroke(255, 250, 218);
  line(x0, y0, x1, y1);
}

public void draw() {
  if (frameCount > fCount) {
    frameCount = 0;
    noLoop();
  }
  // println(frameCount);

  // HACK: move faster through frames - higher resolution
  pushMatrix();
  drawFrame();
  popMatrix();

  pushMatrix();
  drawFrame();
  popMatrix();

  pushMatrix();
  drawFrame();
  popMatrix();

  pushMatrix();
  drawFrame();
  popMatrix();

  // video
  // saveFrame("output/frame########.png");
  // gif
  if (frameCount % fDiv == 0) saveFrame("output/frame####.gif");
}

class Pendulum {
  PVector location;
  PVector origin;
  float r;
  float angle;
  float aVelocity;
  float aAcceleration;
  float damping;

  Pendulum(float angle, float r) {
    this.angle = angle;
    this.r = r;

    origin = new PVector();
    location = new PVector();

    aVelocity = 0.0f;
    aAcceleration = 0.0f;
    damping = 0.997f;
  }

  public void update() {
    float gravity = 0.3f;
    aAcceleration = (-1 * gravity / r) * sin(angle);
    aVelocity += aAcceleration;
    angle += aVelocity;
    aVelocity *= damping;
  }

  public float getHorizPos() {
    return r * sin(angle);
  }

  public void draw() {
    location.set(r * sin(angle), r * cos(angle), 0);
    location.add(origin);

    line(origin.x, origin.y, location.x, location.y);
    ellipse(location.x, location.y, 24, 24);
  }
}
  public void settings() {  size(640, 640);  smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "day51" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
