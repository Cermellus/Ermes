/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestComponent } from 'app/entities/credit-request/credit-request.component';
import { CreditRequestService } from 'app/entities/credit-request/credit-request.service';
import { CreditRequest } from 'app/shared/model/credit-request.model';

describe('Component Tests', () => {
    describe('CreditRequest Management Component', () => {
        let comp: CreditRequestComponent;
        let fixture: ComponentFixture<CreditRequestComponent>;
        let service: CreditRequestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestComponent],
                providers: []
            })
                .overrideTemplate(CreditRequestComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditRequestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CreditRequest(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.creditRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
