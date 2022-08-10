package MyRobot;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.*;
import robocode.*;
import robocode.util.*;
import static java.lang.Math.*;


public class IceColo extends AdvancedRobot {
	class Enemy {
		  public String name;
		  public Double pos;
		  public double energy;
		  public boolean live;
		}

	int m = 30;
  Hashtable<String, Enemy> targets = new Hashtable();
  Enemy target = new Enemy();

  Double myPos, lastPos, next;
  double myEnergy;

  public void run() {
	  setColors(Color.BLACK, Color.BLACK, Color.BLACK);
      setAdjustGunRobot(true);
      setAdjustRadarForGunTurn(true);
      setTurnRadarRightRadians(1.0 / 0.0);

      while(true) {
          myPos = new Double(getp_X(), getp_Y());
          myEnergy = getEnergy();
          if (target.live && getTime() > 9) {
              doWork();
          }

          execute();
      }
  }

  public void onRobotDeath(RobotDeathEvent e) {
      (targets.get(e.getName())).live = false;
  }

  private Double calcPoint(Double p, double dist, double ang) {
      return new Double(p.x + dist * sin(ang), p.y + dist * cos(ang));
  }

  private double calcAngle(Double p2, Double p1) {
      return atan2(p2.x - p1.x, p2.y - p1.y);
  }

  public void doWork() {
	  
	  //int i = 0;
	  //double vr = myEnergy/6.0;
	  //Double p =target.pos;
      int i = 0;
      double vr = myEnergy / 6.0;
      Double p = target.pos;
      double dist;
      double angle = min(min(vr, 1300 / (dist = myPos.distance(target.pos))), target.energy / 3);

      if (getGunTurnRemaining() == 0 && myEnergy > 1) {
          setFire(angle);
      }

      setTurnGunLeftRadians(Utils.normalRelativeAngle(getGunHeadingRadians() - calcAngle(p, myPos)));
      if (next == null) {
          next = lastPos = myPos;
      }

      if ((angle = myPos.distance(next)) < 15.0) {
          angle = 1.0 - rint(pow(random(), (double)getO()));

          do {
              if ((new java.awt.geom.Rectangle2D.Double(
                      30, 30,
                      getBFW() - 60,
                      getBFH() - 60))
                      .contains(
                              p = calcPoint(myPos,
                                      Math.min(dist * 0.8, 100 + 200 * Math.random()),
                                      Math.PI*2 * Math.random()))
                      && evaluate(p, angle) < evaluate(next, angle)) {
                  next = p;
              }
          } while(i++ < 100);

          lastPos = myPos;
      } else {
          setAhead(angle * (
                  (angle = Utils.normalRelativeAngle(calcAngle(next, myPos) - getHeadingRadians()))
                  ==
                  (angle = atan(tan(angle))) ? 1 : -1));

          setTurnRightRadians(angle);
          setMaxVelocity(abs(angle) > 1 ? 0 : 8);
      }

  }

  public double evaluate(Double p, double addLast) {
      double eval = addLast * 0.08 / p.distanceSq(lastPos);
      Enumeration<Enemy> enu = targets.elements();

      while(enu.hasMoreElements()) {
          Enemy en = enu.nextElement();
          if (en.live) {
              eval += min(en.energy / myEnergy, 2) *
                      (1 + abs(cos(calcAngle(myPos, p) - calcAngle(en.pos, p)))) /
                      p.distanceSq(en.pos);
          }
      }

      return eval;
  }

  public void onScannedRobot(ScannedRobotEvent e) {
      if (getO() == 1) {
          setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
      }

      Enemy en;
      String eName;
      if ((en = targets.get(eName = e.getName())) == null) {
          en = new Enemy();
          targets.put(eName, en);
      }

      en.name = eName;
      en.energy = e.getEnergy();
      en.live = true;
      en.pos = calcPoint(myPos, e.getDistance(), getHeadingRadians() + e.getBearingRadians());
      if (!target.live || e.getDistance() < myPos.distance(target.pos)) {
          target = en;
      }
  }
}