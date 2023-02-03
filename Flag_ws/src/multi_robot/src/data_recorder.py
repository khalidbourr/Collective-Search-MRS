#!/usr/bin/env python3.8
import rospy
import csv
import message_filters
from geometry_msgs.msg import Twist
from sensor_msgs.msg import LaserScan
from sensor_msgs.msg import Range

f = open("data.csv", "w")
csvwriter = csv.writer(f)
dataset = []

def data_callback(laser, twist):
    laser_data = laser.range
    twist_data = (twist.linear.x, twist.angular.z)
    data = twist_data  + (laser_data,)
    dataset.append(data)

if __name__ == "__main__":
    scan_sub = message_filters.Subscriber('/robot1/sensor/sonar_front', Range, queue_size = 1)
    cmd_sub = message_filters.Subscriber('/robot1/cmd_vel', Twist, queue_size = 1)

    rospy.init_node('data_recorder')

    ts = message_filters.ApproximateTimeSynchronizer([scan_sub, cmd_sub], 10, 0.1, allow_headerless=True)
    ts.registerCallback(data_callback)

    counter = 0
    rate = rospy.Rate(500)
    while counter<80000:
        rate.sleep()
        counter += 1 
        if counter > 1e5:
            break

    csvwriter.writerows(dataset)
    f.close()
