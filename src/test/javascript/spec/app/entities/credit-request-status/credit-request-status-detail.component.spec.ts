/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestStatusDetailComponent } from 'app/entities/credit-request-status/credit-request-status-detail.component';
import { CreditRequestStatus } from 'app/shared/model/credit-request-status.model';

describe('Component Tests', () => {
    describe('CreditRequestStatus Management Detail Component', () => {
        let comp: CreditRequestStatusDetailComponent;
        let fixture: ComponentFixture<CreditRequestStatusDetailComponent>;
        const route = ({ data: of({ creditRequestStatus: new CreditRequestStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditRequestStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditRequestStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditRequestStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
