import { Formation } from "./formation";

export interface Parcours {
  id: number;
  parcoursName: string;
  parcoursDescription: string;
  imageUrl:string;
  formation ?: Formation
}
