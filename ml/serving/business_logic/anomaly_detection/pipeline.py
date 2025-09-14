from typing import List

class AnomalyDetectionPipeline:
    def __init__(self, model, rules):
        self.model = model
        self.rules = rules

    def detect(self, series: List[float]) -> List[int]:
        return []
