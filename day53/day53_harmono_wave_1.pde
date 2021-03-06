/*== DAY 53 == [TUE FEB 30 2016] == */
/*
 * "Harmonograph - III"
 * Variation on drawing method from yesterday
 */

int fCount = 360/2; // circle yo!
int fDiv = 4;

float freqX = 11;
float freqY = 13;
float phi = 90;

void setup() {
  size(640, 640);
  smooth();
  frameRate(30);
  background(242, 92, 130);
}

void draw() {
  if (frameCount > fCount) {
    frameCount = 0;
    noLoop();
    return;
  }
  // println(frameCount);

  background(242, 92, 130, 220);
  // fill(242, 92, 130, 220);
  // noStroke();
  // rect(0,0,width,height);

  // 0 to target frequency
  float fX = map(sin(radians(frameCount)), 0, 1, 0, freqX);
  float fY = map(sin(radians(frameCount)), 0, 1, 0, freqY);

  float angle;
  float x0 = 0;
  float y0 = 0;
  float amp = width*0.4;
  boolean first = true;
  int points = width*2; // precision
  translate(width/2, height/2);
  for (int i = 0; i < points; i++) {
    angle = map(i, 0, points, 0, TWO_PI);

    float x = amp * sin(angle * fX + radians(phi));
    float y = amp * sin(angle * fY);

    // modulate line width to approximate hand drawn lines
    float dist = dist(x0, y0, x, y);
    if (dist > 0 && dist < 14) {
      float weight = map(dist, 0, 14, 4.0, 1.5);
      strokeWeight(weight);
      stroke(
        map(weight, 0.2, 3.0, 60, 21),
        map(weight, 0.2, 3.0, 147, 56),
        map(weight, 0.2, 3.0, 200, 103));
    }

    if (first) {
      first = false;
    } else {
      line(x0, y0, x, y);
    }

    x0 = x;
    y0 = y;
  }

  // video
  // saveFrame("output/frame########.png");
  // gif
  if (frameCount % fDiv == 0) saveFrame("output/frame####.gif");
}
