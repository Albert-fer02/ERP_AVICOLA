from typing import Protocol, Dict, Any

class HRM(Protocol):
    def score(self, prompt: str, candidate: str) -> Dict[str, Any]: ...
