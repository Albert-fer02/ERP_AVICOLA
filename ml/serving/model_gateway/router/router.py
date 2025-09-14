from typing import List, Dict, Any

class ModelRouter:
    def __init__(self, providers: Dict[str, Any], policies: List[Any]):
        self.providers = providers
        self.policies = policies

    def select(self, request: Dict[str, Any]) -> Any:
        scores = {name: sum(policy.score(name, request) for policy in self.policies) for name in self.providers}
        return self.providers[max(scores, key=scores.get)]
