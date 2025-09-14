class Counter:
    def __init__(self, name: str):
        self.name = name
        self.value = 0

    def inc(self, amount: int = 1):
        self.value += amount

requests_total = Counter("serving_requests_total")
