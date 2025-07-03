import { Parcours } from "./parcours";

export interface Step {
  id: number;
  title: string;
  description: string;
  parcours: Parcours;
  durationInMinutes: number ;
  stepProcess: string;
  imageUrl: string; 
  completed: boolean | null;
  startTime: string | null;
  endTime: string | null;
  status: boolean;
  count: number;
  progress: number;
}
