class LatencyPolicy:
    def __init__(self, avg_latency_ms: dict):
        self.latencies = avg_latency_ms

    def score(self, provider_name: str, request: dict) -> float:
        return -self.latencies.get(provider_name, 0.0)
