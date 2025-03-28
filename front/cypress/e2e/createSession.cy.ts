describe('Create Session spec', () => {
    it('should create a session', () => {

      // 1. Test de connexion réussie
      cy.visit('/login')
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'Teacher',
          firstName: 'New',
          lastName: 'User',
          admin: true,
        },
      }).as('loginRequest')
      const sessions = [
        { id: 1, name: 'Yoga Session', description: 'Relaxation and stretching', time: '10:00 AM' },
        { id: 2, name: 'Pilates Session', description: 'Core strengthening', time: '02:00 PM' },
      ]
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: sessions,
      }).as('sessionsRequest')
      const teachers = [
        {
          id: 1,
          lastName: 'User',
          firstName: 'New',
          createdAt: '2023-01-01T00:00:00.000Z',
          updatedAt: '2023-01-01T00:00:00.000Z',
        },
      ]
      cy.intercept('GET', '/api/teacher', {
        statusCode: 200,
        body: teachers,
      }).as('teachersRequest')
      cy.get('input[formControlName=email]').type('newuser@studio.com')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type="submit"]').click()
      cy.wait('@loginRequest')
      cy.url().should('include', '/sessions')

      // 2. Test de création de session
      cy.get('button').contains('Create').click()
      cy.wait('@teachersRequest')
      cy.get('mat-select[formControlName=teacher_id]').click()
      cy.get('mat-option').contains('New User').click()
      const newSession = {
        id: 3,
        name: 'Yoga Session',
        description: 'Relaxation and stretching',
        date: '2025-03-23',
        teacher_id: 1,
      }
      cy.intercept('POST', '/api/session', {
        statusCode: 200,
        body: newSession,
      }).as('createSessionRequest')
      cy.get('input[formControlName=name]').type('Yoga Session')
      cy.get('input[formControlName=date]').type('2025-03-23')
      cy.get('textarea[formControlName=description]').type('Relaxation and stretching')
      cy.get('button[type="submit"]').click()
      cy.wait('@createSessionRequest').its('response.statusCode').should('eq', 200)
      cy.url().should('include', '/sessions')

    });
});