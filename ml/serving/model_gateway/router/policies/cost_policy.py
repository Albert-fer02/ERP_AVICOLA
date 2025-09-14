class CostPolicy:
    def __init__(self, cost_per_1k_tokens: dict):
        self.costs = cost_per_1k_tokens

    def score(self, provider_name: str, request: dict) -> float:
        return -self.costs.get(provider_name, 0.0)
