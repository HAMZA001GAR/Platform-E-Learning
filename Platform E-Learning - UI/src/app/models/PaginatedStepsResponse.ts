import { Step } from "./step";

export interface PaginatedStepsResponse {
    content: Step[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
  }
  