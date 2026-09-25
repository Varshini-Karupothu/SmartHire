import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SkillAnalyzer } from './skill-analyzer';

describe('SkillAnalyzer', () => {
  let component: SkillAnalyzer;
  let fixture: ComponentFixture<SkillAnalyzer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SkillAnalyzer],
    }).compileComponents();

    fixture = TestBed.createComponent(SkillAnalyzer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
