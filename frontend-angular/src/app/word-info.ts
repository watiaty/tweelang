import {Translation} from "./translation";
import {Language} from "./language";

export class WordInfo {
  id: String;
  word: String;
  language?: Language;
  translations: Translation[];
  definition: String;
  synonyms: String[];
  antonyms: String[];
  derived: String[];
  mainWord: String;

  constructor(id: String, word: String, language: String, translations: Translation[], definition: String, synonyms: String[], antonyms: String[], derived: String[], mainWord: String) {
    this.id = id;
    this.word = word;
    this.translations = translations;
    this.definition = definition;
    this.synonyms = synonyms;
    this.antonyms = antonyms;
    this.derived = derived;
    this.mainWord = mainWord;
  }

}
