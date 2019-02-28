/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditReasonDetailComponent } from 'app/entities/credit-reason/credit-reason-detail.component';
import { CreditReason } from 'app/shared/model/credit-reason.model';

describe('Component Tests', () => {
    describe('CreditReason Management Detail Component', () => {
        let comp: CreditReasonDetailComponent;
        let fixture: ComponentFixture<CreditReasonDetailComponent>;
        const route = ({ data: of({ creditReason: new CreditReason(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReasonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditReasonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditReasonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditReason).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
