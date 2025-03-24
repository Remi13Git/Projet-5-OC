import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { DetailComponent } from './detail.component';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { SessionService } from '../../../../services/session.service';
import { ActivatedRoute } from '@angular/router';

// Mocks des services
const mockSessionApiService = {
  all: jest.fn(),
  detail: jest.fn().mockReturnValue(of({
    id: 1,
    teacher_id: 2,
    users: [1], 
    name: 'Test Session',
    description: 'Test Description',
    date: new Date('2025-03-23')
  })),
  delete: jest.fn(),
  create: jest.fn(),
  update: jest.fn(),
  participate: jest.fn(),
  unParticipate: jest.fn(),
};

const mockTeacherService = {
  all: jest.fn(),
  detail: jest.fn().mockReturnValue(of({
    id: 2,
    name: 'John Doe',
    lastName: 'Doe',
    firstName: 'John',
  }))
};

const mockSessionService = {
  sessionInformation: {
    admin: true,
    id: 1
  }
};

// Créez un stub pour simuler le comportement d'ActivatedRoute
class ActivatedRouteStub {
  snapshot = {
    paramMap: {
      get: (key: string) => {
        if (key === 'id') return '1'; // Simule un paramètre d'ID
        return null;
      }
    }
  };
}

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MatSnackBarModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatIconModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
        { provide: SessionService, useValue: mockSessionService },
        { provide: ActivatedRoute, useClass: ActivatedRouteStub }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call fetchSession on ngOnInit and populate session and teacher', () => {
    const mockSession = {
      id: 1,
      teacher_id: 2,
      users: [1],
      name: 'Test Session',
      description: 'Test Description',
      date: new Date('2025-03-23')
    };

    const mockTeacher = {
      id: 2,
      name: 'John Doe',
      lastName: 'Doe',
      firstName: 'John',
    };

    component.ngOnInit();

    // Vérifier que fetchSession a appelé les services avec les bons paramètres
    expect(mockSessionApiService.detail).toHaveBeenCalledWith('1');
    expect(mockTeacherService.detail).toHaveBeenCalledWith('2');


    // Vérifier que l'état de la participation est bien mis à jour
    expect(component.isParticipate).toBe(true);
  });
});
