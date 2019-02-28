import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditRequestLine } from 'app/shared/model/credit-request-line.model';
import { CreditRequestLineService } from './credit-request-line.service';

@Component({
    selector: 'jhi-credit-request-line-delete-dialog',
    templateUrl: './credit-request-line-delete-dialog.component.html'
})
export class CreditRequestLineDeleteDialogComponent {
    creditRequestLine: ICreditRequestLine;

    constructor(
        protected creditRequestLineService: CreditRequestLineService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditRequestLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditRequestLineListModification',
                content: 'Deleted an creditRequestLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-request-line-delete-popup',
    template: ''
})
export class CreditRequestLineDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditRequestLine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditRequestLineDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditRequestLine = creditRequestLine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/credit-request-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/credit-request-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
