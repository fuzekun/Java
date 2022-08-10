package MyRobot;

import robocode.*;

import java.awt.Color;

/**

 * SnippetBot - a robot by Alisdair Owens

 * This bot includes all sorts of useful snippets.  It is not

 * designed to be a good fighter (although it does well 1v1),

 * just to show how certain things are done

 * Bits of code lifted from Nicator and Chrisbot

 * Conventions in this bot include: Use of radians throughout

 * Storing absolute positions of enemy bots rather than relative ones

 * Very little code in events

 * These are all good programming practices for robocode

 * There may also be methods that arent used; these might just be useful for you.

 */

public class SnippetBot extends AdvancedRobot

{

    /**

     * run: SnippetBot's default behavior

     */

    Enemy target;                   //our current enemy  ������֣������˶��ֵ��������ò���

    final double PI = Math.PI;          //just a constant 

    int direction = 1;              //direction we are heading...1 = forward, -1 = backwards

                                    //����̹�˳�ͷ�ķ���

    double firePower;                   //the power of the shot we will be using - set by do firePower() �������ǵĻ���

 

    public void run() 

    {

        target = new Enemy();               //ʵ����Enemy()��

        target.distance = 100000;           //initialise the distance so that we can select a target

        setColors(Color.red,Color.blue,Color.green);    //sets the colours of the robot

        //the next two lines mean that the turns of the robot, gun and radar are independant

        //��gun,radar������̹�˳�
        setAdjustGunRobot(true);
        setAdjustRadarRobot(true);
        setAdjustRadarForGunTurn(true);

        turnRadarRightRadians(2*PI);            //turns the radar right around to get a view of the field �Ի��ȼ�����תһ��

        

        while(true) 

        {

            doMovement();               //Move the bot �ƶ�������

            doFirePower();              //select the fire power to use ѡ�����

            doScanner();                //Oscillate the scanner over the bot ɨ��

            doGun();                    //move the gun to predict where the enemy will be Ԥ����ˣ������ڹ�

            out.println(target.distance);       

            setFire(firePower);            //���ж�����ɺ󣬿���

            execute();              //execute all commands  ����ʹ�õĶ�ΪAdvancedRobot���еķ���������

                                    //����Ȩ�����ǣ����������������������ؿ��Ƹ�������

        }

    }


    /*

      * This simple function calculates the fire power to use (from 0.1 to 3)

      * based on the distance from the target.  We will investigate the data structure

      * holding the target data later.

      */

    void doFirePower() 

    {

        firePower = 400/target.distance;//selects a bullet power based on our distance away from the target 

                                        //���ݵ��˾�����ѡ�����,��Ϊ����ǰ��������Ϊ300�����Ի����������

    }


    /*

      * This is the movememnt function.  It will cause us

      * to circle strafe the enemy (ie move back and forward,

      * circling the enemy.  if you don't know what strafing means

      * play more quake.

      * The direction variable is global to the class.  Passing a

      * negative number to setAhead causes the bot to go backwards

      * ��Ŀ�����������ذڶ�

      */

    void doMovement() 

    {

        if (getTime()%20 == 0)  //?��20�ı���ʱ��ͷ�ת����

        {

            //every twenty 'ticks'

            direction *= -1;        //reverse direction

            setAhead(direction*300);    //move in that direction

        }

        setTurnRightRadians(target.bearing + (PI/2)); //every turn move to circle strafe the enemy 

                                                      //ÿһʱ�������Ե���Ϊ������Բ�˶�

    }

    /*

    * this scanner method allows us to make our scanner track our target.

    * it will track to where our target is at the moment, and some further

    * in case the target has moved.  This way we always get up to the minute

    * information on our target   �״�����Ŀ��

    */

    void doScanner() 

    {

        double radarOffset;  //�״�ƫ����

        if (getTime() - target.ctime > 4) //???why����ɨ��4���غ϶�ûɨ����ζʧȥ��Ŀ�꣬��ȫɨһ��

        {

            //if we haven't seen anybody for a bit....

            radarOffset = 360;      //rotate the radar to find a target

        } 

        else 

        {

            //next is the amount we need to rotate the radar by to scan where the target is now

            //ͨ��ɨ������״���ת�Ļ��ȣ�"������ԭ����������Ŀ������www.robochina.org".�״ﻡ��-���˽Ƕȵõ��������Ϊ��תֵ

            radarOffset = getRadarHeadingRadians() - absbearing(getBFH(),getBFH(),target.x,target.y);

            //this adds or subtracts small amounts from the bearing for the radar to produce the wobbling

            //and make sure we don't lose the target

            //�ڵõ��ĽǶ��мӻ��һ��Ƕȣ����״��С�ķ�Χ�ڰڶ���ʧȥĿ��

            if (radarOffset < 0)

            radarOffset -= PI/8;  //(0.375)

            else

            radarOffset += PI/8; 

        }

        //turn the radar

        setTurnRadarLeftRadians(NormaliseBearing(radarOffset)); //��ת����ת���Ƕȵ�PI��

    }

    /*

    * This simple method moves the gun to the bearing that we predict the

    * enemy will be by the time our bullet will get there.

    * the 'absbearing' method can be found in the helper functions section

    * the nextX and nextY method can be found in the 'Enemy' class description

    */

    void doGun() 

    {

        //works out how long it would take a bullet to travel to where the enemy is *now*

        //this is the best estimation we have 

        //�����ӵ�����Ŀ���ʱ�䳤speed = 20 - 3 * power;�м��㹫ʽ,������ٶ�=ʱ��

        long time = getTime() + (int)(target.distance/(20-(3*firePower)));

        //offsets the gun by the angle to the next shot based on linear targeting provided by the enemy class

        //��ֱ��ΪĿ�꣬ƫ���ӵ���һ�η���ĽǶȡ����������ӵ���յļ��ʼ��١����Ը������ĺ���Բ�˶��Ļ����������⣩

        //target.guesssX(),target.guessY()ΪĿ���ƶ��������

        double gunOffset = getGunHeadingRadians() - absbearing(getBFH(),getBFH(),target.x,target.y);

        setTurnGunLeftRadians(NormaliseBearing(gunOffset));  //������ԽǶȵ�2PI��

    }

    /*

    * This set of helper methods.  You may find several of these very useful

    * They include the ability to find the angle to a point.

    */

    //if a bearing is not within the -pi to pi range, alters it to provide the shortest angle

    double NormaliseBearing(double ang) 

    {

        if (ang > PI)

        ang -= 2*PI;

        if (ang < -PI)

        ang += 2*PI;

        return ang;

    }

    //if a heading is not within the 0 to 2pi range, alters it to provide the shortest angle

    double NormaliseHeading(double ang) 

    {

        if (ang > 2*PI)

        ang -= 2*PI;

        if (ang < 0)

        ang += 2*PI;

        return ang;

    }

    //returns the distance between two x,y coordinates '**'

    //�����߳���������֮��ľ���

    public double getrange( double x1,double y1, double x2,double y2 )

    {

        double xo = x2-x1;

        double yo = y2-y1;

        double h = Math.sqrt( xo*xo + yo*yo ); 

        return h;   

    }

    //gets the absolute bearing between to x,y coordinates

    //����x,y������������ԽǶȣ���"��������"����ֱ������ϵ��������Ƕȡ�������

    public double absbearing( double x1,double y1, double x2,double y2 )

    {

        double xo = x2-x1;

        double yo = y2-y1;

        double h = getrange( x1,y1, x2,y2 );

        if( xo > 0 && yo > 0 )

        {

            //�����Ҷ��壬�Ա߳�б�ߵû���.��robocode�еľ��Է���ϵ������ϵ����

            //x,yΪ�����Ͻ�Ϊ0-90,x��y�����½�Ϊ90-180,x,y�����½�180-270,x����y�����Ͻ�270-360

            //�˴�Ҫ���robocode�еľ��ԽǶ�����Ϊ0,��Ϊ180����������Ϊ�㻮��������õ�����Ľ��

            return Math.asin( xo / h );

        }

        if( xo > 0 && yo < 0 )

        {

            return Math.PI - Math.asin( xo / h ); //xΪ��,yΪ���ڶ����޽�

        }

        if( xo < 0 && yo < 0 )

        {

            return Math.PI + Math.asin( -xo / h ); //����������180+�Ƕ�

        }

        if( xo < 0 && yo > 0 )

        {

            return 2.0*Math.PI - Math.asin( -xo / h ); //������360-�Ƕ�

        }

        return 0;

    }

    /**

    * onScannedRobot: What to do when you see another robot

     * ɨ���¼�,Ҳ�ǳ�ʼ��Ŀ�����ݵĹ���

    */

    public void onScannedRobot(ScannedRobotEvent e) 

    {

        //if we have found a closer robot....

        if ((e.getDistance() < target.distance)||(target.name == e.getName())) 

        {

            //the next line gets the absolute bearing to the point where the bot is

            //��ö��ֵľ��Ի���

            double absbearing_rad = (getHeadingRadians()+e.getBearingRadians())%(2*PI);

            //this section sets all the information about our target

            target.name = e.getName();

            //��ö��ֵ�x,y���꣬��"robocode����ԭ��֮��������"����

            target.x = getBFH()+Math.sin(absbearing_rad)*e.getDistance(); //works out the x coordinate of where the target is

            target.y = getBFH()+Math.cos(absbearing_rad)*e.getDistance(); //works out the y coordinate of where the target is

            target.bearing = e.getBearingRadians();

            target.head = e.getHeadingRadians();

            target.ctime = getTime();               //game time at which this scan was produced ɨ�赽�����˵���Ϸʱ��

            target.speed = e.getVelocity();         //�õ������ٶ�

            target.distance = e.getDistance();

        }

    }

    public void onRobotDeath(RobotDeathEvent e) 

    {

        if (e.getName() == target.name)

        target.distance = 10000; //this will effectively make it search for a new target

    }   

 

}

 

 

/*

* This class holds scan data so that we can remember where enemies were

* and what they were doing when we last scanned then.

* You could make a hashtable (with the name of the enemy bot as key)

* or a vector of these so that you can remember where all of your enemies are

* in relation to you.

* This class also holds the guessX and guessY methods. These return where our targeting

* system thinks they will be if they travel in a straight line at the same speed

* as they are travelling now.  You just need to pass the time at which you want to know

* where they will be.

* ��������ɨ�赽��Ŀ��������������ݣ�Ҳ����hashtable��vector�����������к������йص�Ŀ������(����Ⱥս)

* �м��guessX,guessY�����������ֱ�߾����˶�������һ������

*/

class Enemy 

{

    /*

     * ok, we should really be using accessors and mutators here,

     * (i.e getName() and setName()) but life's too short.

     */

    String name;

    public double bearing;

    public double head;

    public long ctime; //game time that the scan was produced

    public double speed;

    public double x,y;

    public double distance;

//    public double guessX(long when)
//
//    {
//
//        //��ɨ��ʱ���ӵ������ʱ��� �� ����ٶ�=����, ���ö��ֵ���������ƶ�����õ������ƶ��������
//
//        long diff = when - ctime;
//
//        return x+Math.sin(head)*speed*diff; //Ŀ���ƶ��������
//
//    }
//
//    public double guessY(long when)
//
//    {
//
//        long diff = when - ctime;
//
//        return y+Math.cos(head)*speed*diff;
//
//    }

}