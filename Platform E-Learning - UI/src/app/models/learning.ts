import { Step } from "./step";

export interface Learning {
    id: number;
    title: string;
    description: string;
    url: string[];
    steps: Step
}
