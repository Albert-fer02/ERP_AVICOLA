class CompliancePolicy:
    def __init__(self, restricted_providers: set):
        self.restricted_providers = restricted_providers

    def score(self, provider_name: str, request: dict) -> float:
        return -1e6 if provider_name in self.restricted_providers else 0.0
