import {WordUsage} from "./word-usage";
import {WordShort} from "./word-short";
import {Transcription} from "./transcription";

export class Word {
  id!: number;
  text!: String;
  language!: String;
  transcriptions!: Transcription[];
  wordUsages!: WordUsage[];
  synonyms!: WordShort[];
  antonyms!: WordShort[];
  formedBy!: WordShort;
  weight!: number;
}
