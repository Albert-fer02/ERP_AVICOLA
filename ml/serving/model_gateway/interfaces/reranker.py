from typing import Protocol, List, Tuple

class Reranker(Protocol):
    def rerank(self, query: str, documents: List[str]) -> List[Tuple[int, float]]: ...
