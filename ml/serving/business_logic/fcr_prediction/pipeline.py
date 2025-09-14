from typing import Dict, Any

class FCRPredictionPipeline:
    def __init__(self, model):
        self.model = model

    def predict(self, features: Dict[str, Any]) -> float:
        return 1.75
