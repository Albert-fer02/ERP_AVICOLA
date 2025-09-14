// index.tsx - Main dashboard page
// Justification: Minimalist UI focused on usability.
// Displays KPIs for lots, uses Tailwind for styling.
// Fetches data from backend API, handles loading and errors.
// Responsive design for mobile.

import { useEffect, useState } from 'react';
import axios from 'axios';

interface LotSummary {
  nombre: string;
  edadDias: number;
  fcr: number | null;
  mortalityRate: number | null;
}

export default function Dashboard() {
  const [lots, setLots] = useState<LotSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchLots = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/lotes');
        const lotSummaries = await Promise.all(
          response.data.map(async (lot: any) => {
            const summaryResponse = await axios.get(`http://localhost:8081/api/kpis/lote/${lot.id}`);
            return summaryResponse.data;
          })
        );
        setLots(lotSummaries);
      } catch (err) {
        setError('Failed to load data');
      } finally {
        setLoading(false);
      }
    };
    fetchLots();
  }, []);

  if (loading) return <div className="flex justify-center items-center h-screen">Loading...</div>;
  if (error) return <div className="text-red-500 text-center">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-100 p-4">
      <h1 className="text-2xl font-bold text-center mb-6">Dashboard de Lotes</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {lots.map((lot, index) => (
          <div key={index} className="bg-white p-4 rounded shadow">
            <h2 className="text-lg font-semibold">{lot.nombre}</h2>
            <p>Edad: {lot.edadDias} días</p>
            <p>FCR: {lot.fcr?.toFixed(2) || 'N/A'}</p>
            <p>Mortalidad: {lot.mortalityRate?.toFixed(2) || 'N/A'}%</p>
          </div>
        ))}
      </div>
    </div>
  );
}