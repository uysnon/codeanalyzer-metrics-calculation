import tensorflow as tf
from tensorflow import keras
model = keras.models.load_model("model_code_metrics.h5")
result = model.predict([[2,2,2,2,2,2]])
print(result)