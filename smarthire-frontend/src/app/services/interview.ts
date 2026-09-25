import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Interview {
  id?: number;
  company: string;
  role: string;
  round: string;
  date: string;
  time: string;
  feedback: string;
}

@Injectable({
  providedIn: 'root'
})
export class InterviewService {

  private apiUrl =
    'http://localhost:8080/api/interviews';

  constructor(private http: HttpClient) {}

  getInterviews(): Observable<Interview[]> {

    return this.http.get<Interview[]>(
      this.apiUrl
    );

  }

  addInterview(
    interview: Interview
  ): Observable<Interview> {

    return this.http.post<Interview>(
      this.apiUrl,
      interview
    );

  }

  deleteInterview(
    id: number
  ): Observable<string> {

    return this.http.delete(
      `${this.apiUrl}/${id}`,
      {
        responseType: 'text'
      }
    );

  }

}

