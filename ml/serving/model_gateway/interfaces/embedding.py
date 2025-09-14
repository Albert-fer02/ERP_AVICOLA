from typing import Protocol, List

class Embeddings(Protocol):
    def embed(self, texts: List[str]) -> List[List[float]]: ...
