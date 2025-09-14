from typing import List, Protocol

class Retriever(Protocol):
    def retrieve(self, query: str, *, top_k: int = 5) -> List[str]: ...

class RAGPipeline:
    def __init__(self, llm, retriever: Retriever):
        self.llm = llm
        self.retriever = retriever

    def run(self, question: str) -> str:
        docs = self.retriever.retrieve(question, top_k=5)
        context = "\n".join(docs)
        prompt = f"Contexto:\n{context}\n\nPregunta: {question}\nRespuesta:"
        return self.llm.generate(prompt)
