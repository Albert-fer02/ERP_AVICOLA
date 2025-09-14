from typing import List, Optional, Dict, Any

class VLLMProvider:
    def __init__(self, endpoint: str, model_name: str):
        self.endpoint = endpoint
        self.model_name = model_name

    def generate(self, prompt: str, *, system: Optional[str] = None, params: Optional[Dict[str, Any]] = None) -> str:
        return "[vllm] stub response"

    def chat(self, messages: List[Dict[str, str]], *, params: Optional[Dict[str, Any]] = None) -> str:
        return "[vllm] stub chat response"
