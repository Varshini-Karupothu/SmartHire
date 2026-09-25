import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SkillAnalysis {
  id?: number;
  jobSkills: string;
  mySkills: string;
  matchedSkills: string;
  missingSkills: string;
  matchPercentage: number;
}

@Injectable({
  providedIn: 'root'
})
export class SkillAnalysisService {

  private apiUrl =
    'http://localhost:8080/api/skill-analysis';

  constructor(private http: HttpClient) {}

  analyzeSkills(
    analysis: SkillAnalysis
  ): Observable<SkillAnalysis> {

    return this.http.post<SkillAnalysis>(
      this.apiUrl,
      analysis
    );

  }

  getAnalyses(): Observable<SkillAnalysis[]> {

    return this.http.get<SkillAnalysis[]>(
      this.apiUrl
    );

  }

}

