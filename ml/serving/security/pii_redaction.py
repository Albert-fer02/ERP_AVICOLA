import re

def redact_pii(text: str) -> str:
    text = re.sub(r"\b\d{8}\b", "<DNI>", text)
    text = re.sub(r"\b\d{9}\b", "<RUC>", text)
    return text
