import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  private apiUrl = `${environment.apiUrl}/api/test`;

  constructor(private http: HttpClient) {}

  testBackend(): Observable<string> {
    return this.http.get(this.apiUrl, {
      responseType: 'text'
    });
  }
}