import { Routes } from '@angular/router';

import { Login } from './features/login/login';
import { Register } from './features/register/register';
import { Dashboard } from './features/dashboard/dashboard';
import { Applications } from './features/applications/applications';
import { Interviews } from './features/interviews/interviews';
import { SkillAnalyzer } from './features/skill-analyzer/skill-analyzer';
import { MockInterview } from './features/mock-interview/mock-interview';
import { Resume } from './features/resume/resume';
import { Notifications } from './features/notifications/notifications';
import { Layout } from './shared/components/layout/layout';

export const routes: Routes = [

  { path: '', component: Login },

  { path: 'login', component: Login },

  { path: 'register', component: Register },

  {
    path: '',
    component: Layout,
    children: [

      { path: 'dashboard', component: Dashboard },

      { path: 'applications', component: Applications },

      { path: 'interviews', component: Interviews },

      { path: 'skill-analyzer', component: SkillAnalyzer },

      { path: 'mock-interview', component: MockInterview },

      { path: 'resume', component: Resume },

      { path: 'notifications', component: Notifications }

    ]
  }

];