from tensorflow import keras
model = keras.models.load_model("model_code_metrics.h5")
# model = keras.models.load_model("D:\\java\\projects\\codeanalyzer-metrics-calculation\\online-calculation\\target\\classes\\model_code_metrics.h5")
result = model.predict([[7.0000	,0.281046,	0.313725,	0.209150	,0.648352	,0.351648]])[0][1]
