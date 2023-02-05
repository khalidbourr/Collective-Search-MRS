# Collective_Search_MRS
ROS and X-klaim for programming robot collective search

############################ Scenario ###################### 


In this scenario, four robots are tasked with locating every flag in the area without prior knowledge of their positions. The robots move randomly, avoid obstacles, and get information on their proximity to the flags. Once the distance falls below a certain threshold, it indicates that the flags have been discovered, and all other robots are notified. When every flag has been located, all robots halt.

############################ Install ######################## 

git clone https://github.com/khalidbourr/Collective_Search_MRS

cd Collective_Search_ws/Flag_ws/src/multi_robot/worlds

sudo cp -r red_flag ~/.gazebo/models
sudo cp -r green_flag ~/.gazebo/models
sudo cp -r blue_flag ~/.gazebo/models
sudo cp -r white_flag ~/.gazebo/models
sudo cp -r basic_box ~/.gazebo/models
sudo cp -r walls_evry ~/.gazebo/models

cd Flag_ws

catkin_make

########################### run scenario #########################


terminal 1: 

cd Flag_ws
source devel/setup.bash
roslaunch rosbridge_server rosbridge_websocket.launch 

terminal 2

cd Flag_ws 
source devel/setup.bash
roslaunch multi_robot multi_robot.launch 

