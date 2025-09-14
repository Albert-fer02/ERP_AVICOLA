from typing import List, Optional, Dict, Any

class DeepSeekProvider:
    def __init__(self, model_name: str):
        self.model_name = model_name

    def generate(self, prompt: str, *, system: Optional[str] = None, params: Optional[Dict[str, Any]] = None) -> str:
        return "[deepseek] stub response"

    def chat(self, messages: List[Dict[str, str]], *, params: Optional[Dict[str, Any]] = None) -> str:
        return "[deepseek] stub chat response"
