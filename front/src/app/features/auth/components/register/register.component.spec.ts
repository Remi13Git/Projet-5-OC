import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('RegisterComponent (Integration)', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceMock: { register: jest.Mock };
  let routerMock: { navigate: jest.Mock };

  beforeEach(async () => {
    // Création du mock pour AuthService
    authServiceMock = {
      // On s'assure que register retourne toujours un observable, même pour un succès
      register: jest.fn().mockReturnValue(of(undefined))
    };

    // Création du mock pour Router
    routerMock = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        RouterTestingModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the Register component', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService.register and navigate on successful registration', () => {
    // Remplir le formulaire avec des données valides
    component.form.setValue({
      email: 'testuser@example.com',
      firstName: 'Test',
      lastName: 'User',
      password: 'password123'
    });

    // Appeler submit()
    component.submit();

    // Vérifier que AuthService.register a été appelé avec les bonnes données
    expect(authServiceMock.register).toHaveBeenCalledWith({
      email: 'testuser@example.com',
      firstName: 'Test',
      lastName: 'User',
      password: 'password123'
    });

    // Vérifier que le router a navigué vers '/login'
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should handle registration error correctly', () => {
    // Configurer register pour qu'il retourne une erreur
    authServiceMock.register.mockReturnValue(throwError(() => new Error('Registration failed')));

    component.form.setValue({
      email: 'testuser@example.com',
      firstName: 'Test',
      lastName: 'User',
      password: 'password123'
    });

    component.submit();

    expect(authServiceMock.register).toHaveBeenCalled();
    expect(component.onError).toBe(true);
  });

  it('should validate the form before submitting', () => {
    // Tenter de soumettre le formulaire sans données
    component.submit();

    // Vérifier que le formulaire est invalide
    expect(component.form.valid).toBeFalsy();
  });
});
