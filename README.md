# Capstone-Bangkit
# Description
Information
Tourist Attractions Prediction is used to predict which tourist attractions can be visited with the weather on that day and the location of that day. 
Users only need to enter a location and recommendations for places based on that day will appear

# Modelling
![image]
(https://github.com/aaaqnes/Capstone-Bangkit/assets/70590066/3da8cdb2-9f32-49d8-9208-baf9c1d03801)
The data we use is from Kaggle titled "Indonesia Tourism Destination" which consists of 450 data points. 
In our model, we employ a classification technique to determine the best places to visit based on weather and city.
The working principle of our model is as follows:
Architecture: We utilize a sequential model with three layers, namely, one input layer, two dense layers, and one output layer.
Input layer: It receives input with the shape of (feature_dim), which corresponds to the feature dimensions.
Dense layers: We have two dense layers, the first one with 128 units and the second one with 64 units. Both layers use the ReLU activation function.
Output layer: It is a dense layer with num_classes units, employing the softmax activation function to generate probabilities for each class.
