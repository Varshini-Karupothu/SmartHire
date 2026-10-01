import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, timeout, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

export interface MockQuestion {
  id?: number;
  role: string;
  difficulty: string;
  topic: string;
  question: string;
  expectedKeywords?: string;
  answer?: string;
}

export interface MockAnswerRequest {
  questionId: number;
  answer: string;
}

export interface QuestionResult {
  questionId: number;
  score: number;
  feedback: string;
  missingKeywords: string[];
}

export interface MockEvaluationResponse {
  score: number;
  results: QuestionResult[];
}

@Injectable({
  providedIn: 'root'
})
export class MockInterviewService {

  private apiUrl =
    `${environment.apiUrl}/api/mock-interview`;

  constructor(private http: HttpClient) {}

  getQuestions(
    role: string,
    difficulty: string,
    topic: string
  ): Observable<MockQuestion[]> {

    const params = new HttpParams()
      .set('role', role)
      .set('difficulty', difficulty)
      .set('topic', topic);

    console.log('Calling API...');
    console.log('Role:', role);
    console.log('Difficulty:', difficulty);
    console.log('Topic:', topic);

    return this.http
      .get<MockQuestion[]>(
        `${this.apiUrl}/questions`,
        { params }
      )
      .pipe(
        timeout(10000),
        catchError(error => {

          console.error(
            'Mock Interview API Error:',
            error
          );

          return throwError(() => error);
        })
      );
  }

  evaluateAnswers(
    answers: MockAnswerRequest[]
  ): Observable<MockEvaluationResponse> {

    console.log(
      'Sending answers for evaluation:',
      answers
    );

    return this.http
      .post<MockEvaluationResponse>(
        `${this.apiUrl}/evaluate`,
        answers
      )
      .pipe(
        timeout(10000),
        catchError(error => {

          console.error(
            'Evaluation API Error:',
            error
          );

          return throwError(() => error);
        })
      );
  }
}