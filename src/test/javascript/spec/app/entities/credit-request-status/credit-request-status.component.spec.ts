/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestStatusComponent } from 'app/entities/credit-request-status/credit-request-status.component';
import { CreditRequestStatusService } from 'app/entities/credit-request-status/credit-request-status.service';
import { CreditRequestStatus } from 'app/shared/model/credit-request-status.model';

describe('Component Tests', () => {
    describe('CreditRequestStatus Management Component', () => {
        let comp: CreditRequestStatusComponent;
        let fixture: ComponentFixture<CreditRequestStatusComponent>;
        let service: CreditRequestStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestStatusComponent],
                providers: []
            })
                .overrideTemplate(CreditRequestStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditRequestStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CreditRequestStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.creditRequestStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
