export interface StepPayload {
    title: string;
    description: string;
    durationInMinutes: number;
    parcoursId: number | null;
    stepProcess: string;
    imageUrl: String;
  }